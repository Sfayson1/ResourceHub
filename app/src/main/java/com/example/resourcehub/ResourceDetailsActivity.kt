package com.example.resourcehub

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResourceDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_details)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val resource: Resource? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("resource", Resource::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("resource") as? Resource
        }

        if (resource == null) {
            Toast.makeText(this, "No resource found.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        findViewById<TextView>(R.id.txtName).text = resource.name
        findViewById<TextView>(R.id.txtAddress).text = resource.address
        findViewById<TextView>(R.id.txtPhone).text = resource.phone
        findViewById<TextView>(R.id.txtHours).text = resource.hours

        val btnAddFavorite = findViewById<Button>(R.id.btnAddFavorite)
        val btnRemoveFavorite = findViewById<Button>(R.id.btnRemoveFavorite)

        fun updateFavoriteButtons() {
            if (AppData.favorites.any { it.name == resource.name }) {
                btnAddFavorite.visibility = View.GONE
                btnRemoveFavorite.visibility = View.VISIBLE
            } else {
                btnAddFavorite.visibility = View.VISIBLE
                btnRemoveFavorite.visibility = View.GONE
            }
        }

        updateFavoriteButtons()

        btnAddFavorite.setOnClickListener {
            if (!AppData.favorites.any { it.name == resource.name }) {
                AppData.favorites.add(resource)
                AppData.saveFavorites()
                Toast.makeText(this, "Added to favorites!", Toast.LENGTH_SHORT).show()
                updateFavoriteButtons()
            }
        }

        btnRemoveFavorite.setOnClickListener {
            val removed = AppData.favorites.removeAll { it.name == resource.name }
            if (removed) {
                AppData.saveFavorites()
                Toast.makeText(this, "Removed from favorites.", Toast.LENGTH_SHORT).show()
                updateFavoriteButtons()
            }
        }
    }
}
