package com.example.lebonangle

import com.example.lebonangle.api.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {

    @GET("categories")
    fun getCategories(): Call<CategoriesJson>

    @GET("adverts")
    fun getAdverts(): Call<AdvertsJson>

    @GET("adverts")
    fun getAdvertsFromCategory(@Query("category.id") categoryId: Int): Call<AdvertsJson>

    @GET("pictures")
    fun getPicturesFromAdvert(@Query("advert.id") advertId: Int): Call<PicturesJson>
}