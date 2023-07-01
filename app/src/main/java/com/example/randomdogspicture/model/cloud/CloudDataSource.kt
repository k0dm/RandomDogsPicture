package com.example.randomdogspicture.model.cloud

interface CloudDataSource {
    fun fetch(callback: DogCloudCallback)
}