package com.example.randomdogspicture.model.cloud.retrofit


import com.example.randomdogspicture.model.cloud.LinkDogPictureCloud
import retrofit2.Call
import retrofit2.http.GET

interface LinkDogPictureService {

    @GET("https://dog.ceo/api/breeds/image/random")
    fun getPicture(): Call<LinkDogPictureCloud>
}

interface ServiceCallback {

    fun returnSuccess(data: LinkDogPictureCloud)

    fun returnError(error: com.example.randomdogspicture.view.Error)
}




