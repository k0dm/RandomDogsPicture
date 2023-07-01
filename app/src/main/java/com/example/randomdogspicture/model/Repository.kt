package com.example.randomdogspicture.model

import com.example.randomdogspicture.model.cloud.CloudDataSource
import com.example.randomdogspicture.model.cloud.DogCloud
import com.example.randomdogspicture.model.cloud.LinkDogPictureCloudCallback
import com.example.randomdogspicture.view.DogUI
import com.example.randomdogspicture.view.Error

interface Repository {

    fun init(dataCallback: DataCallback)

    fun clear()

    fun getPicture()

    fun changeStatus()

    class Base(
        private val cloudDataSource: CloudDataSource,
        manageResources: ManageResources,
        private val badPicture: Error = Error.BadPicture(manageResources),
        private val failedLoading: Error = Error.FailedLoading(manageResources)


    ) : Repository {

        private var dataCallback: DataCallback = DataCallback.Empty()
        override fun init(dataCallback: DataCallback) {
            this.dataCallback = dataCallback
        }

        override fun getPicture() {
            cloudDataSource.getPicture(object : LinkDogPictureCloudCallback {

                override fun provide(dogCloud: DogCloud) {
                    dataCallback.provideDog(dogCloud.toDogUi())
                }

                override fun fail(error: Error) {
                    dataCallback.provideError(error)
                }

            })

        }

        override fun changeStatus() {
            TODO("Not yet implemented")
        }


        override fun clear() {
            this.dataCallback = DataCallback.Empty()
        }

    }
}

