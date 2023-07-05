package com.example.randomdogspicture.model

import androidx.annotation.DrawableRes
import com.example.randomdogspicture.presentation.Error

interface DataUiCallback: ErrorProvider{
    fun provideUrl(url: String)

    fun provideIconResId(@DrawableRes id: Int)

    class Empty : DataUiCallback {
        override fun provideUrl(url: String) = Unit

        override fun provideError(error: Error) = Unit

        override fun provideIconResId(id: Int) = Unit
    }
}