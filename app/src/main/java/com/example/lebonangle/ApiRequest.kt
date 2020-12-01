package com.example.lebonangle

import com.example.lebonangle.api.CategoriesJson
import com.example.lebonangle.api.CategoriesJsonItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {

    @GET("categories")
    fun getCategories(): Call<CategoriesJson>

    /*@GET("categories/${id}")
    fun getCategory(id:Int): Call<CategoriesJsonItem>*/
}