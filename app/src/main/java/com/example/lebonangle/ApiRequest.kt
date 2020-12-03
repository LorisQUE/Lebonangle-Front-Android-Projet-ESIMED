package com.example.lebonangle

import com.example.lebonangle.api.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiRequest {
    @GET("categories")
    fun getCategories(): Call<CategoriesJson>

    @GET("adverts")
    fun getAdverts(): Call<AdvertsJson>

    @GET("adverts")
    fun getAdvertsFromCategory(@Query("category.id") categoryId: Int): Call<AdvertsJson>

    @GET("pictures")
    fun getPicturesFromAdvert(@Query("advert.id") advertId: Int): Call<PicturesJson>

    @Headers("Content-type: application/json")
    @POST("adverts")
    fun postAdvert(@Body advert:AdvertsJsonItemPost): Call<AdvertsJsonItem>
}