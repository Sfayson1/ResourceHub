package com.example.resourcehub

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val txtDetails = findViewById<TextView>(R.id.txtDetails)

        // Receive data from previous screen
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")

        txtDetails.text = "$name\n\n$description"
    }
}
