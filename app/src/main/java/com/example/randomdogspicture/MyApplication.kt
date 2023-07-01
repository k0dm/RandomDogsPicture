package com.example.randomdogspicture

import android.app.Application

import com.example.randomdogspicture.model.ManageResources
import com.example.randomdogspicture.model.Repository
import com.example.randomdogspicture.model.cache.CacheDataSource
import com.example.randomdogspicture.model.cloud.BaseCloudDataSource
import com.example.randomdogspicture.model.cloud.DogService
import com.example.randomdogspicture.presentation.ViewModel
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
            Repository.Base(
                BaseCloudDataSource(
                    retrofit.create(DogService::class.java),
                    manageResources
                ),
                CacheDataSource.Fake(manageResources),
//                TestCloudDataSource(),
                manageResources
            )
        )
    }
}