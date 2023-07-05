package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.ErrorProvider
import com.example.randomdogspicture.presentation.Error

interface DogCloudCallback :ErrorProvider{

    fun provide(dogCloud: DogCloud)

    //fun provideError(error: com.example.randomdogspicture.presentation.Error)
}