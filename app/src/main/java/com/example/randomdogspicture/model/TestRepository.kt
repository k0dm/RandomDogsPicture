package com.example.randomdogspicture.model

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.randomdogspicture.R
import com.example.randomdogspicture.model.cloud.CloudDataSource
import com.example.randomdogspicture.model.cloud.LinkDogPictureCloud
import com.example.randomdogspicture.view.Error
import com.example.randomdogspicture.viewmodel.ResultCallback

class TestRepository(
    private val cloudDataSource: CloudDataSource,
    private val manageResources: ManageResources
) : Repository {


    private val noConnection by lazy { Error.NoConnection(manageResources) }
    private var resultCallback: ResultCallback = ResultCallback.Empty()


    override fun getPicture(imageView: ImageView) {

        cloudDataSource.getPicture(object : LinkDogPictureCloudCallback {

            override fun provide(linkDogPictureCloud: LinkDogPictureCloud) {

                linkDogPictureCloud.map(object : DataCallback {
                    override fun provideUrl(url: String) {

                        Glide.with(imageView.context)
                            .load(url).listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    Log.d("glide", "failed")
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    Log.d("glide", "success")
                                    return false
                                }
                            })
                            .placeholder(R.drawable.baseline_cloud_download_100)
                            .into(imageView)
                        resultCallback.provideSuccess()
                    }
                })
            }

            override fun fail(error: Error) {
                resultCallback.provideError(noConnection)
            }

        })
    }


    override fun init(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    override fun clear() {
        resultCallback = ResultCallback.Empty()
    }


}