package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.AdvertsAdapter
import com.example.lebonangle.adapter.CategoriesAdapter
import com.example.lebonangle.api.AdvertsJson
import kotlinx.android.synthetic.main.activity_adverts.*
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AdvertsActivity : AppCompatActivity() {
    private var TAG = "Adverts"
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverts)
        var category = intent.getSerializableExtra("category") as Int

        println("CATEGORY EST ICI : " + category)

        getCurrentData(category)
    }

    private fun getCurrentData(categ:Int){
        Log.d(TAG, "getCurrentData")
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
                println(data)

                withContext(Dispatchers.Main){

                    //On insère les datas dans la vue lol
                    val advertAdapter: AdvertsAdapter = AdvertsAdapter(context, data)
                    recyclerViewAdverts.adapter = advertAdapter
                    recyclerViewAdverts.layoutManager = LinearLayoutManager(context)

                }
            }
        }
    }
}