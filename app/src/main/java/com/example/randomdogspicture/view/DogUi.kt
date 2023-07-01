package com.example.randomdogspicture.view

import androidx.annotation.DrawableRes
import com.example.randomdogspicture.R
import com.example.randomdogspicture.model.DataCallback
import com.example.randomdogspicture.viewmodel.ResultCallback


abstract class DogUI(private val link: String, @DrawableRes val iconResId: Int) {

    fun provide(resultCallback: ResultCallback) = with(resultCallback) {
        provideUrl(link)
        provideIconResId(iconResId)
    }

    class Base(link: String) : DogUI(link, R.drawable.baseline_favorite_border_36)

    class Favorite(link: String) : DogUI(link, R.drawable.baseline_favorite_36)

}