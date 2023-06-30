package com.example.randomdogspicture.model

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.randomdogspicture.model.cloud.CloudDataSource
import com.example.randomdogspicture.model.cloud.LinkDogPictureCloud
import com.example.randomdogspicture.model.cloud.LinkDogPictureCloudCallback
import com.example.randomdogspicture.view.Error
import com.example.randomdogspicture.viewmodel.ResultCallback

interface Repository {

    fun init(resultCallback: ResultCallback)

    fun clear()

    fun getPicture()

    fun changeStatus()

    class Base(
        private val cloudDataSource: CloudDataSource,
        manageResources: ManageResources,
        private val badPicture: Error = Error.BadPicture(manageResources),
        private val failedLoading: Error = Error.FailedLoading(manageResources)


    ) : Repository {

        private var resultCallback: ResultCallback = ResultCallback.Empty()
        override fun init(resultCallback: ResultCallback) {
            this.resultCallback = resultCallback
        }

        override fun getPicture() {
            cloudDataSource.getPicture(object : LinkDogPictureCloudCallback {

                override fun provide(linkDogPictureCloud: LinkDogPictureCloud) {
                    Log.d("BaseRepo", "good link")

                    linkDogPictureCloud.map(object : DataCallback {
                        @SuppressLint("CheckResult")
                        override fun provideUrl(url: String) {
                            resultCallback.provideUrl(url)
                        }
                    })
                }

                override fun fail(error: Error) {
                    resultCallback.provideError(error)
                }

            })

        }

        override fun changeStatus() {
            TODO("Not yet implemented")
        }


        override fun clear() {
            this.resultCallback = ResultCallback.Empty()
        }

    }
}

