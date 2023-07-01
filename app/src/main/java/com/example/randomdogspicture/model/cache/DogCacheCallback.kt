package com.example.randomdogspicture.model.cache

import com.example.randomdogspicture.model.cloud.DogCloud

interface DogCacheCallback {

    fun provide(dogCloud: DogCloud)

    fun fail(error: com.example.randomdogspicture.presentation.Error)
}