package com.example.randomdogspicture.model

import android.graphics.Bitmap
import com.example.randomdogspicture.presentation.Error

interface BitmapCallback: ErrorProvider{

    fun provideSuccess(bitmap: Bitmap)
}