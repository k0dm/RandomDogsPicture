package com.example.randomdogspicture.presentation

import androidx.annotation.DrawableRes
import com.example.randomdogspicture.R


abstract class DogUI(private val link: String, @DrawableRes val iconResId: Int) {

    fun provide(resultUiCallback: ResultUiCallback) = with(resultUiCallback) {
        provideUrl(link)
        provideIconResId(iconResId)
    }

    class Base(link: String) : DogUI(link, R.drawable.baseline_favorite_border_36)

    class Favorite(link: String) : DogUI(link, R.drawable.baseline_favorite_36)

}