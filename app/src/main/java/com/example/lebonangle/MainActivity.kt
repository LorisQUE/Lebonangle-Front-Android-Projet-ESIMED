package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainMenuButtonCategories.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        })

        mainMenuButtonAnnonces.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, AdvertsActivity::class.java)
            startActivity(intent)
        })

        mainMenuButtonCreationAnnonce.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, AdvertCreateActivity::class.java)
            startActivity(intent)
        })
    }
}