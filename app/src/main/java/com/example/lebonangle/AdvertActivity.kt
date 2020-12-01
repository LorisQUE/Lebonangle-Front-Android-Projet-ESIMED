package com.example.lebonangle

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lebonangle.api.AdvertsJsonItem
import kotlinx.android.synthetic.main.activity_advert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AdvertActivity : AppCompatActivity() {
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advert)

        var currentAdvert = intent.getSerializableExtra("advert") as AdvertsJsonItem

        val price = currentAdvert.price.toString() + "€"
        val publishedAt:String
        println("TAG publishedAt")
        if(!currentAdvert.publishedAt.isNullOrEmpty()) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(currentAdvert.publishedAt)
            publishedAt = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(date!!)
        } else {
            publishedAt = ""
        }
        println("TAG isNullOrEmpty")

        txtAnnonceTitreEdit.setText(currentAdvert.title)
        txtAnnonceAuteurEdit.setText(currentAdvert.author)
        txtAnnonceContenusEdit.setText(currentAdvert.content)
        txtAnnonceMailEdit.setText(currentAdvert.email)
        txtAnnoncePrixEdit.setText(price)
        txtAnnoncePublishedEdit.setText(publishedAt)
        println("TAG settt")


        progressBarAdvert.visibility = View.GONE
    }


}