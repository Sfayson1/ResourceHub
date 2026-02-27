package com.example.resourcehub

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<TextView>(R.id.rowLogout).setOnClickListener {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
        }

        setupBottomNav()
    }

    private fun setupBottomNav() {
        findViewById<TextView>(R.id.navHomeProfile).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<TextView>(R.id.navFavoritesProfile).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
        findViewById<TextView>(R.id.navBudgetProfile).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
        findViewById<TextView>(R.id.navProfileProfile).setOnClickListener {
            // Already on Profile
        }
    }
}