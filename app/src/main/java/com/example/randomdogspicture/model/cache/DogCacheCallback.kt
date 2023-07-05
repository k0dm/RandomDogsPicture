package com.example.randomdogspicture.model.cache

import com.example.randomdogspicture.model.ErrorProvider
import com.example.randomdogspicture.model.cloud.DogCloud

interface DogCacheCallback: ErrorProvider{

    fun provide(dogCloud: DogCloud)

    //fun provideError(error: com.example.randomdogspicture.presentation.Error)
}