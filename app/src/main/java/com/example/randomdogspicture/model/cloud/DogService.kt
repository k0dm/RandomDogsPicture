package com.example.randomdogspicture.model.cloud


import retrofit2.Call
import retrofit2.http.GET

interface DogService {

    @GET("https://dog.ceo/api/breeds/image/random")
    fun getPicture(): Call<DogCloud>
}


