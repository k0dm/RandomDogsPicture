package com.example.randomdogspicture.model

import com.example.randomdogspicture.view.DogUI
import java.lang.Error

interface DataCallback {

    fun provideDog(dogUI: DogUI)
    fun provideError(error: com.example.randomdogspicture.view.Error)

    class Empty : DataCallback {
        override fun provideDog(dogUI: DogUI) = Unit
        override fun provideError(error: com.example.randomdogspicture.view.Error) = Unit
    }
}