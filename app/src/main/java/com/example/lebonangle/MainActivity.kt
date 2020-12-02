package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

const val BASE_URL_API = "http://10.0.2.2:8000/api/" //10.0.2.2 permet à l'émulateur d'accèder au localhost
const val BASE_URL = "http://10.0.2.2:8000/"

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
            intent.putExtra("category", 0)
            startActivity(intent)
        })

        mainMenuButtonCreationAnnonce.setOnClickListener( View.OnClickListener {
            val intent = Intent(this, AdvertCreateActivity::class.java)
            startActivity(intent)
        })
    }
}