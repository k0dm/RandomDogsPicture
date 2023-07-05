package com.example.randomdogspicture.model

import com.example.randomdogspicture.presentation.DogUI

interface DogUiCallback: ErrorProvider {

    fun provideDog(dogUI: DogUI)

    class Empty : DogUiCallback {
        override fun provideDog(dogUI: DogUI) = Unit
        override fun provideError(error: com.example.randomdogspicture.presentation.Error) = Unit
    }
}