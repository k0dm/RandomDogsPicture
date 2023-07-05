package com.example.randomdogspicture.model

import com.example.randomdogspicture.presentation.Error
interface ErrorProvider {

    fun provideError(error:Error )
}