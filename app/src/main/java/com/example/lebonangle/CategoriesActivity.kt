package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "http://10.0.2.2:8000/api/" //10.0.2.2 permet à l'émulateur d'accèder au localhost

class CategoriesActivity : AppCompatActivity() {

    private var TAG = "Categories"
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        getCurrentData()


    }

    private fun getCurrentData(){
        Log.d(TAG, "getCurrentData")
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        println(TAG + "_" + BASE_URL + "  " + api.getCategories().toString())

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCategories().awaitResponse()
            if(response.isSuccessful){
                val data = response.body()!!
                Log.d(TAG, data[0].toString())
                println(data)

                withContext(Dispatchers.Main){

                    //On insère les datas dans la vue lol
                    val categAdapter:CategoriesAdapter = CategoriesAdapter(context, data)
                    recyclerViewCategories.adapter = categAdapter
                    recyclerViewCategories.layoutManager = LinearLayoutManager(context)

                }
            }
        }
    }

}
