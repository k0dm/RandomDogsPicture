package com.example.randomdogspicture.model

import com.example.randomdogspicture.model.cache.CacheDataSource
import com.example.randomdogspicture.model.cache.DogCacheCallback
import com.example.randomdogspicture.model.cloud.CloudDataSource
import com.example.randomdogspicture.model.cloud.DogCloud
import com.example.randomdogspicture.model.cloud.DogCloudCallback
import com.example.randomdogspicture.presentation.Error

interface Repository {

    fun init(dataCallback: DataCallback)

    fun clear()

    fun fetch(selection: PictureSelection)

    fun changeDogPictureStatus(dataCallback: DataCallback)

    fun chooseFavorites(fromCache: Boolean)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource,
        manageResources: ManageResources,
        private val badPicture: Error = Error.BadPicture(manageResources),
        private val failedLoading: Error = Error.FailedLoading(manageResources)


    ) : Repository {

        private var getFromCache = false
        private var cachedDogCloud: DogCloud? = null

        private var dataCallback: DataCallback = DataCallback.Empty()
        override fun init(dataCallback: DataCallback) {
            this.dataCallback = dataCallback
        }

        override fun fetch(selection: PictureSelection) {

            if (getFromCache) {
                cacheDataSource.fetch(selection, object : DogCacheCallback {
                    override fun provide(dogCloud: DogCloud) {
                        cachedDogCloud = dogCloud
                        dataCallback.provideDog(dogCloud.toFavoriteDogUi())                    }

                    override fun fail(error: Error) {
                        dataCallback.provideError(error)
                    }
                })
            } else {
                cloudDataSource.fetch(object : DogCloudCallback {

                    override fun provide(dogCloud: DogCloud) {
                        cachedDogCloud = dogCloud
                        dataCallback.provideDog(dogCloud.toDogUi())
                    }

                    override fun fail(error: Error) {
                        cachedDogCloud = null
                        dataCallback.provideError(error)
                    }

                })
            }

        }


        override fun changeDogPictureStatus(dataCallback: DataCallback) {
            cachedDogCloud?.let {
                dataCallback.provideDog(
                    it.change(cacheDataSource)
                )
            }
        }

        override fun chooseFavorites(fromCache: Boolean) {
            getFromCache = fromCache
        }


        override fun clear() {
            this.dataCallback = DataCallback.Empty()
        }

    }
}

