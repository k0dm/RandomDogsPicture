package com.example.randomdogspicture.model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.example.randomdogspicture.presentation.Error
import androidx.core.content.ContextCompat
import com.example.randomdogspicture.model.cache.CacheDataSource
import com.example.randomdogspicture.model.cache.DogCacheCallback
import com.example.randomdogspicture.model.cloud.CloudDataSource
import com.example.randomdogspicture.model.cloud.DogCloud
import com.example.randomdogspicture.model.cloud.DogCloudCallback


interface Repository {

    fun init(dogUiCallback: DogUiCallback)

    fun clear()

    fun fetch(selection: PictureSelection)

    fun changeDogPictureStatus(dogUiCallback: DogUiCallback)

    fun chooseFavorites(fromCache: Boolean)
    fun downloadPicture(resultCallback: ResultCallback)

    class Base(
        private val context: Context,
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource,
        private val downloadManager: DownloadManager
    ) : Repository {

        private var getFromCache = false
        private var cachedDogCloud: DogCloud? = null

        private var dogUiCallback: DogUiCallback = DogUiCallback.Empty()
        override fun init(dogUiCallback: DogUiCallback) {
            this.dogUiCallback = dogUiCallback
        }

        override fun fetch(selection: PictureSelection) {

            if (getFromCache) {
                cacheDataSource.fetch(selection, object : DogCacheCallback {
                    override fun provide(dogCloud: DogCloud) {
                        cachedDogCloud = dogCloud
                        dogUiCallback.provideDog(dogCloud.toFavoriteDogUi())
                    }

                    override fun provideError(error: Error) {
                        dogUiCallback.provideError(error)
                    }
                })
            } else {
                cloudDataSource.fetch(object : DogCloudCallback {

                    override fun provide(dogCloud: DogCloud) {
                        cachedDogCloud = dogCloud
                        dogUiCallback.provideDog(dogCloud.toDogUi())
                    }

                    override fun provideError(error: Error) {
                        cachedDogCloud = null
                        dogUiCallback.provideError(error)
                    }

                })
            }

        }


        override fun changeDogPictureStatus(dogUiCallback: DogUiCallback) {
            cachedDogCloud?.let {
                dogUiCallback.provideDog(
                    it.change(cacheDataSource)
                )
            }
        }

        override fun chooseFavorites(fromCache: Boolean) {
            getFromCache = fromCache
        }

        override fun downloadPicture(resultCallback: ResultCallback) {

            cachedDogCloud?.download(downloadManager, object : ResultCallback {
                override fun provideSuccess() {
                    downloadManager.save(resultCallback)
                }

                override fun provideError(error: Error) {
                    resultCallback.provideError(error)
                }

            })
        }


        private fun updateOrRequestPermission() {
            val hasWritePermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        }

        override fun clear() {
            this.dogUiCallback = DogUiCallback.Empty()
        }

    }
}

