package com.example.lebonangle

import com.example.lebonangle.api.AdvertsJson
import com.example.lebonangle.api.AdvertsJsonItem
import com.example.lebonangle.api.CategoriesJson
import com.example.lebonangle.api.CategoriesJsonItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("categories")
    fun getCategories(): Call<CategoriesJson>

    @GET("adverts")
    fun getAdverts(): Call<AdvertsJson>

    @GET("adverts")
    fun getAdvertsFromCategory(@Query("category.id") categoryId: Int): Call<AdvertsJson>

}