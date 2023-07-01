package com.example.randomdogspicture.model.cloud

interface DogCloudCallback {

    fun provide(dogCloud: DogCloud)

    fun fail(error: com.example.randomdogspicture.presentation.Error)
}