package com.example.androiddevchallenge

data class Dog(
    val id: Int = -1,
    val imageUrl: String = "",
    val name: String? = null,
    val breed: String? = null,
    val sex: String? = null)

data class DogResult(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int)
