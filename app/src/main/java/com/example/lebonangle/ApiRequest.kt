package com.example.lebonangle

import com.example.lebonangle.api.*
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiRequest {
    @Headers("accept: application/json")
    @GET("categories")
    fun getCategories(): Call<CategoriesJson>

    @Headers("accept: application/json")
    @GET("adverts")
    fun getAdverts(): Call<AdvertsJson>

    @Headers("accept: application/json")
    @GET("adverts")
    fun getAdvertsFromCategory(@Query("category.id") categoryId: Int): Call<AdvertsJson>

    @Headers("accept: application/ld+json")
    @POST("adverts")
    fun postAdvert(@Body advert:AdvertsJsonItemPost): Call<AdvertsJsonItem>

    @Headers("accept: application/json")
    @GET("pictures")
    fun getPicturesFromAdvert(@Query("advert.id") advertId: Int): Call<PicturesJson>

    @Headers("accept: application/ld+json")
    @Multipart
    @POST("pictures")
    fun postPicture( @Part file: MultipartBody.Part
    ): Call<PicturesJsonItemPost>
}