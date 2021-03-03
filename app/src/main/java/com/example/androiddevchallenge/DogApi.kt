package com.example.androiddevchallenge

import retrofit2.Call
import retrofit2.http.GET

interface DogApi {
    @GET("images/search")
    fun getDog(): Call<List<DogResult>>?
}
