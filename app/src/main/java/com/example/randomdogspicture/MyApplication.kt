package com.example.randomdogspicture

import android.app.Application
import com.bumptech.glide.Glide
import com.example.randomdogspicture.model.ManageResources
import com.example.randomdogspicture.model.TestRepository
import com.example.randomdogspicture.model.cloud.BaseCloudDataSource
import com.example.randomdogspicture.model.cloud.TestCloudDataSource
import com.example.randomdogspicture.model.cloud.retrofit.LinkDogPictureService
import com.example.randomdogspicture.viewmodel.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    lateinit var viewModel: ViewModel
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.google.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val manageResources = ManageResources.Base(this)
    override fun onCreate() {

        super.onCreate()
        viewModel = ViewModel(
            TestRepository(
                BaseCloudDataSource(retrofit.create(LinkDogPictureService::class.java), manageResources),
                manageResources
            )
        )
    }
}