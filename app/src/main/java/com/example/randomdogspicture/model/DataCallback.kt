package com.example.randomdogspicture.model

import com.example.randomdogspicture.presentation.DogUI

interface DataCallback {

    fun provideDog(dogUI: DogUI)
    fun provideError(error: com.example.randomdogspicture.presentation.Error)

    class Empty : DataCallback {
        override fun provideDog(dogUI: DogUI) = Unit
        override fun provideError(error: com.example.randomdogspicture.presentation.Error) = Unit
    }
}