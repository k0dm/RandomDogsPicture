package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.DataCallback
import com.google.gson.annotations.SerializedName

class LinkDogPictureCloud(
    @SerializedName("message")
    private val url: String
) {
    fun map(dataCallback: DataCallback) {
        dataCallback.provideUrl(url)
    }
}