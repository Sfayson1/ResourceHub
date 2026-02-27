package com.example.resourcehub

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    private val allResources = listOf(
        Resource("Downtown Food Bank", "123 Main St, Los Angeles, CA", "(213) 555-0101", "9 AM - 5 PM", "Food"),
        Resource("Sunny Side Shelter", "456 Oak St, Los Angeles, CA", "(213) 555-0102", "24/7", "Housing"),
        Resource("Community Job Center", "789 Pine St, Los Angeles, CA", "(213) 555-0103", "8 AM - 4 PM", "Jobs"),
        Resource("Health First Clinic", "101 Elm St, Los Angeles, CA", "(213) 555-0104", "10 AM - 6 PM", "Utilities"),
        Resource("Second Harvest", "222 Maple St, Los Angeles, CA", "(213) 555-0201", "8 AM - 2 PM", "Food"),
        Resource("Hope House", "333 Birch St, Los Angeles, CA", "(213) 555-0202", "24/7", "Housing")
    )

    private lateinit var lvResources: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val displayedResources = mutableListOf<Resource>()
    private val selectedCategories = mutableSetOf<String>()
    private var currentZip: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val edtLocation = findViewById<EditText>(R.id.edtLocationSearch)

        edtLocation.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL) {
                currentZip = edtLocation.text.toString().trim()
                updateResourcesList()
                true
            } else {
                false
            }
        }

        // --- CATEGORY BUTTONS ---
        setupCategoryButton(R.id.btnCategoryHousing, "Housing")
        setupCategoryButton(R.id.btnCategoryFood, "Food")
        setupCategoryButton(R.id.btnCategoryJobs, "Jobs")
        setupCategoryButton(R.id.btnCategoryUtilities, "Utilities")

        // --- RESOURCES NEARBY ---
        lvResources = findViewById(R.id.lvResourcesNearby)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())
        lvResources.adapter = adapter

        lvResources.setOnItemClickListener { _, _, position, _ ->
            val selected = displayedResources[position]
            val intent = Intent(this, ResourceDetailsActivity::class.java)
            intent.putExtra("resource", selected)
            startActivity(intent)
        }

        setupBottomNav()
    }

    private fun setupCategoryButton(buttonId: Int, category: String) {
        val button = findViewById<MaterialButton>(buttonId)
        button.setOnClickListener {
            if (selectedCategories.contains(category)) {
                selectedCategories.remove(category)
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500)) // Use your default color
            } else {
                selectedCategories.add(category)
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_700)) // Use a selected color
            }
            updateResourcesList()
        }
    }

    private fun updateResourcesList() {
        displayedResources.clear()
        
        // Only show results if a "zipcode" (or any text) has been entered
        if (currentZip.isNotEmpty()) {
            val filtered = if (selectedCategories.isEmpty()) {
                allResources
            } else {
                allResources.filter { selectedCategories.contains(it.category) }
            }
            displayedResources.addAll(filtered)
        }

        adapter.clear()
        adapter.addAll(displayedResources.map { it.name })
        adapter.notifyDataSetChanged()
    }

    private fun setupBottomNav() {
        findViewById<TextView>(R.id.navHome).setOnClickListener { /* Already here */ }
        findViewById<TextView>(R.id.navFavorites).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        findViewById<TextView>(R.id.navBudget).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
        findViewById<TextView>(R.id.navProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
