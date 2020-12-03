package com.example.lebonangle.api

data class CategoriesJsonItem(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}