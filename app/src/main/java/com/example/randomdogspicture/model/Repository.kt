package com.example.randomdogspicture.model

import android.widget.ImageView
import com.example.randomdogspicture.viewmodel.ResultCallback

interface Repository {

    fun init(resultCallback: ResultCallback)

    fun clear()

    fun getPicture(imageView: ImageView)
}

