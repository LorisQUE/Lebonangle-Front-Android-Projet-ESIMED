package com.example.lebonangle

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.PicturesAdapter
import com.example.lebonangle.api.AdvertsJsonItem
import com.example.lebonangle.api.AdvertsJsonItemPost
import com.example.lebonangle.api.CategoriesJsonItem
import kotlinx.android.synthetic.main.activity_advert.*
import kotlinx.android.synthetic.main.activity_advert_create.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AdvertCreateActivity : AppCompatActivity() {

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advert_create)
        setSpinnerCategories()

        //Ajout d'une annonce
        btnValider.setOnClickListener(View.OnClickListener {
            if (advertCreateAuteurEdit.text.isNullOrEmpty() || advertCreateMailEdit.text.isNullOrEmpty() || advertCreateContenueEdit.text.isNullOrEmpty() ||
                advertCreatePrixEdit.text.isNullOrEmpty() || advertCreateTitreEdit.text.isNullOrEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }

            val newAdvert = AdvertsJsonItemPost(
                advertCreateAuteurEdit.text.toString(),
                "/api/categories/" + (advertCreateCategoryEdit.selectedItem as CategoriesJsonItem).id,
                advertCreateContenueEdit.text.toString(),
                advertCreateMailEdit.text.toString(),
                advertCreatePrixEdit.text.toString().toFloat(),
                advertCreateTitreEdit.text.toString()
            )

            GlobalScope.launch(Dispatchers.IO) {
                val response = api.postAdvert(newAdvert).awaitResponse()

                if (response.isSuccessful){
                    println("SUCCES")
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        })
    }

    private fun setSpinnerCategories(){
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCategories().awaitResponse()
            if (response.isSuccessful){
                val categories = response.body()!!
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter<CategoriesJsonItem>(this@AdvertCreateActivity, android.R.layout.simple_spinner_item, categories)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    advertCreateCategoryEdit.adapter = adapter;
                }
            }
        }
    }


}