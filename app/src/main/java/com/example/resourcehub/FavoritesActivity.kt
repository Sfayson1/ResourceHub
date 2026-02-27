package com.example.resourcehub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class FavoritesActivity : AppCompatActivity() {

    private lateinit var list: ListView
    private lateinit var emptyCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        list = findViewById(R.id.listFavorites)
        emptyCard = findViewById(R.id.cardEmptyFavorites)

        list.setOnItemClickListener { _, _, position, _ ->
            val selected = AppData.favorites[position]
            val i = Intent(this, ResourceDetailsActivity::class.java)
            i.putExtra("resource", selected)
            startActivity(i)
        }

        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        if (AppData.favorites.isEmpty()) {
            list.visibility = View.GONE
            emptyCard.visibility = View.VISIBLE
        } else {
            list.visibility = View.VISIBLE
            emptyCard.visibility = View.GONE

            val names = AppData.favorites.map { it.name }
            list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        }
    }

    private fun setupBottomNav() {
        findViewById<TextView>(R.id.navHomeFav).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<TextView>(R.id.navFavoritesFav).setOnClickListener {
            // Already on Favorites
        }
        findViewById<TextView>(R.id.navBudgetFav).setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }
        findViewById<TextView>(R.id.navProfileFav).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
