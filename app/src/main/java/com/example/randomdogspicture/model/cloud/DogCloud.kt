package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.DataCallback
import com.example.randomdogspicture.view.DogUI
import com.google.gson.annotations.SerializedName

class DogCloud(
    @SerializedName("message")
    private val url: String
) {
    fun toDogUi() = DogUI.Base(url)
}