package com.example.lebonangle.api

import com.google.gson.annotations.SerializedName

data class PicturesJsonItemPost(
    @SerializedName("@context") val context : String,
    @SerializedName("@id") val id : String,
    @SerializedName("@type") val type : String,
    @SerializedName("contentUrl") val contentUrl : String,
    @SerializedName("advert") val advert : String
)