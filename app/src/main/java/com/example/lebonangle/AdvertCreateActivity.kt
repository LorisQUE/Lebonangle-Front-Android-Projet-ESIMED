package com.example.lebonangle

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lebonangle.adapter.PicturesAdapter
import com.example.lebonangle.adapter.PicturesAdvertCreateAdapter
import com.example.lebonangle.api.AdvertsJsonItemPost
import com.example.lebonangle.api.CategoriesJsonItem
import com.example.lebonangle.api.PicturesJsonItemPost
import kotlinx.android.synthetic.main.activity_advert_create.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AdvertCreateActivity : AppCompatActivity() {

    val PICK_IMAGE = 100
    lateinit var imageUri:Uri

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)

    val arrayPic = ArrayList<String>()
    val arrayImage = ArrayList<String>()

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
                advertCreateTitreEdit.text.toString(),
                arrayPic
            )

            GlobalScope.launch(Dispatchers.IO) {
                val response = api.postAdvert(newAdvert).awaitResponse()
                if (response.isSuccessful){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@AdvertCreateActivity , "Annonces publiées avec succès !", Toast.LENGTH_LONG).show()
                    }
                }
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        //Ajout d'une photo
        btnAjoutPhoto.setOnClickListener(View.OnClickListener {
            openGallery()
        })
    }

    private fun openGallery(){
        val gallery:Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data?.getData()!!

            val parcelFileDescriptor = contentResolver.openFileDescriptor(imageUri!!, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(imageUri!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            val requestFile = RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                file
            )

            api.postPicture(
                MultipartBody.Part.createFormData(
                    "file",
                    file.name,
                    requestFile
                )
            ).enqueue(object:Callback<PicturesJsonItemPost> {

                override fun onFailure(call: Call<PicturesJsonItemPost>, t: Throwable) {
                    println("Error" + t.message)
                }
                override fun onResponse(
                    call: Call<PicturesJsonItemPost>,
                    response: Response<PicturesJsonItemPost>
                ) {
                    response.body()?.let {
                        arrayPic.add(it.id)
                        arrayImage.add(it.contentUrl)

                        val picturesAdapter = PicturesAdvertCreateAdapter(this@AdvertCreateActivity, arrayImage)
                        recyclerViewAdvertPictures.adapter = picturesAdapter
                        recyclerViewAdvertPictures.layoutManager = LinearLayoutManager(this@AdvertCreateActivity)
                    }
                }
            })


        }
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