package com.example.lebonangle.api

import java.io.Serializable

data class AdvertsJsonItemPost(
    val author: String,
    val category: String,
    val content: String,
    val email: String,
    val price: Float,
    val title: String
):Serializable