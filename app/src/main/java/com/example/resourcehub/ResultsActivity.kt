package com.example.resourcehub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    private val dummyResources = listOf(
        Resource("Downtown Food Bank", "123 Main St, Los Angeles, CA", "(213) 555-0101", "9 AM - 5 PM", "Food"),
        Resource("Sunny Side Shelter", "456 Oak St, Los Angeles, CA", "(213) 555-0102", "24/7", "Shelter"),
        Resource("Community Job Center", "789 Pine St, Los Angeles, CA", "(213) 555-0103", "8 AM - 4 PM", "Jobs"),
        Resource("Health First Clinic", "101 Elm St, Los Angeles, CA", "(213) 555-0104", "10 AM - 6 PM", "Health")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val location = intent.getStringExtra("location") ?: ""
        val subtitle = findViewById<TextView>(R.id.txtResultsSubtitle)
        subtitle.text = "Showing resources near: $location"

        val listView = findViewById<ListView>(R.id.listResources)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dummyResources.map { it.name })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selected = dummyResources[position]
            val intent = Intent(this, ResourceDetailsActivity::class.java)
            intent.putExtra("resource", selected)
            startActivity(intent)
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<TextView>(R.id.navHomeResults).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<TextView>(R.id.navFavoritesResults).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        findViewById<TextView>(R.id.navBudgetResults).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
        findViewById<TextView>(R.id.navProfileResults).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
