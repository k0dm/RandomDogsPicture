package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.DownloadManager
import com.example.randomdogspicture.model.ResultCallback
import com.example.randomdogspicture.model.cache.CacheDataSource
import com.example.randomdogspicture.presentation.DogUI
import com.google.gson.annotations.SerializedName

class DogCloud(
    @SerializedName("message")
    private val url: String
) {
    fun toDogUi() = DogUI.Base(url)

    fun toFavoriteDogUi() = DogUI.Favorite(url)

    fun change(cacheDataSource: CacheDataSource): DogUI = cacheDataSource.addOrRemove(this)

    fun download(downloadManager: DownloadManager, callback: ResultCallback) = downloadManager.download(url, callback)
}