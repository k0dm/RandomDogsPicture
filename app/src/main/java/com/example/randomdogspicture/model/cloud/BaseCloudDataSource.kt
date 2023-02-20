package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.LinkDogPictureCloudCallback
import com.example.randomdogspicture.model.ManageResources
import com.example.randomdogspicture.model.cloud.retrofit.LinkDogPictureService
import com.example.randomdogspicture.view.Error
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException
import javax.security.auth.callback.Callback

class BaseCloudDataSource(
    private val service: LinkDogPictureService,
    private val manageResources: ManageResources
) : CloudDataSource {

    private val noConnection by lazy { Error.NoConnection(manageResources) }
    private val serviceUnavailable by lazy { Error.ServiceUnavailable(manageResources) }

    override fun getPicture(callback: LinkDogPictureCloudCallback) {
        service.getPicture().enqueue(object : retrofit2.Callback<LinkDogPictureCloud> {
            override fun onResponse(
                call: Call<LinkDogPictureCloud>,
                response: Response<LinkDogPictureCloud>
            ) {
                if (response.isSuccessful) {
                    callback.provide(response.body()!!)
                } else {
                    callback.fail(serviceUnavailable)
                }
            }

            override fun onFailure(call: Call<LinkDogPictureCloud>, t: Throwable) {
                if (t is UnknownHostException) {
                    callback.fail(noConnection)
                } else {
                    callback.fail(serviceUnavailable)
                }
            }

        })
    }
}