package com.example.randomdogspicture.model

import android.content.Context
import androidx.annotation.StringRes

interface ManageResources {

    fun getString(@StringRes stringResId: Int): String

    class Base(private val context: Context) : ManageResources {

        override fun getString(stringResId: Int) = context.getString(stringResId)
    }
}