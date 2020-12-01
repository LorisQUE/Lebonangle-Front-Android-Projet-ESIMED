package com.example.lebonangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

class CategoriesActivity : AppCompatActivity() {
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        getCurrentData()
    }

    private fun getCurrentData(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCategories().awaitResponse()
            if(response.isSuccessful){
                val data = response.body()!!

                withContext(Dispatchers.Main){
                    //On insère les datas dans la vue lol
                    val categAdapter:CategoriesAdapter = CategoriesAdapter(context, data)
                    recyclerViewCategories.adapter = categAdapter
                    recyclerViewCategories.layoutManager = LinearLayoutManager(context)
                    progressBarCategories.visibility = View.GONE
                }
            }
        }
    }

}

