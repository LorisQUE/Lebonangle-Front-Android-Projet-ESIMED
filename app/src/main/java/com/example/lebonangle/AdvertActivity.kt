package com.example.lebonangle

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.AdvertsAdapter
import com.example.lebonangle.adapter.PicturesAdapter
import com.example.lebonangle.api.AdvertsJsonItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_advert.*
import kotlinx.android.synthetic.main.activity_adverts.*
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

        getCurrentData(currentAdvert)
    }

    private fun getCurrentData(advert:AdvertsJsonItem){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getPicturesFromAdvert(advert.id).awaitResponse()

            if(response.isSuccessful){
                val data = response.body()!!

                withContext(Dispatchers.Main){
                    //On insère les datas dans la vue lol
                    val price = advert.price.toString() + "€"
                    val publishedAt:String

                    if(!advert.publishedAt.isNullOrEmpty()) {
                        val date = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).parse(advert.publishedAt)
                        publishedAt = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(date!!)
                    } else {
                        publishedAt = ""
                    }

                    txtAnnonceTitreEdit.setText(advert.title)
                    txtAnnonceAuteurEdit.setText(advert.author)
                    txtAnnonceContenusEdit.setText(advert.content)
                    txtAnnonceMailEdit.setText(advert.email)
                    txtAnnoncePrixEdit.setText(price)
                    txtAnnoncePublishedEdit.setText(publishedAt)

                    val picturesAdapter = PicturesAdapter(context, data)
                    recyclerViewPictures.adapter = picturesAdapter
                    recyclerViewPictures.layoutManager = LinearLayoutManager(context)

                    progressBarAdvert.visibility = View.GONE


                    }

            }

        }
    }
}
