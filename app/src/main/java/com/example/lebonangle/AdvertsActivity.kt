package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.AdvertsAdapter
import com.example.lebonangle.api.AdvertsJson
import kotlinx.android.synthetic.main.activity_adverts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class AdvertsActivity : AppCompatActivity() {
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverts)

        var category = intent.getSerializableExtra("category") as Int

        getCurrentData(category)
    }

    private fun getCurrentData(categ:Int){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response:Response<AdvertsJson>
            if (categ === 0) {
                response = api.getAdverts().awaitResponse()
            } else {
                response = api.getAdvertsFromCategory(categ).awaitResponse()
            }
            if(response.isSuccessful){
                val data = response.body()!!
                withContext(Dispatchers.Main){
                    //On insère les datas dans la vue lol
                    val advertAdapter = AdvertsAdapter(context, data)
                    recyclerViewAdverts.adapter = advertAdapter
                    recyclerViewAdverts.layoutManager = LinearLayoutManager(context)
                    progressBarAdverts.visibility = View.GONE
                }
            }
        }
    }
}