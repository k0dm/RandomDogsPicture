package com.example.randomdogspicture.model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.randomdogspicture.presentation.Error
import java.io.IOException
import java.lang.Exception
import java.net.URL

interface DownloadManager {

    fun save(callback: ResultCallback)

    fun download(url: String, callback: ResultCallback)

    class Base(
        private val context: Context,
        manageResources: ManageResources,
        private val failedLoading: Error = Error.FailedLoading(manageResources),
        private val failedSaving: Error = Error.FailedSaving(manageResources),
        private val failedCreateMediaStore: Error = Error.FailedCreateMediaStore(manageResources)
    ) : DownloadManager {

        private var bitmap: Bitmap? = null

        override fun save(callback: ResultCallback) {
            val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bitmap!!.width)
                put(MediaStore.Images.Media.HEIGHT, bitmap!!.height)
            }

            try {
                context.contentResolver.insert(uri, contentValues)?.also { uri ->
                    context.contentResolver.openOutputStream(uri).use { outputStream ->
                        if (!bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                            callback.provideError(failedSaving)
                        }
                    }
                } ?: callback.provideError(failedCreateMediaStore)
            } catch (e: IOException) {
                callback.provideError(failedSaving)
            }
            callback.provideSuccess()
        }

        override fun download(url: String, callback: ResultCallback) {
            var image: Bitmap
            try {
                val connection = URL(url).openConnection().apply {
                    doInput = true
                    connect()
                    getInputStream().use { inputStream ->
                        image = BitmapFactory.decodeStream(inputStream)
                        bitmap = image
                        callback.provideSuccess()
                    }
                }

            } catch (e: Exception) {
                callback.provideError(failedLoading)
            }

        }
    }
}