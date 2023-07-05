package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.ManageResources
import com.example.randomdogspicture.presentation.Error
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

class BaseCloudDataSource(
    private val service: DogService,
    private val manageResources: ManageResources
) : CloudDataSource {

    private val noConnection by lazy { Error.NoConnection(manageResources) }
    private val serviceUnavailable by lazy { Error.ServiceUnavailable(manageResources) }

    override fun fetch(callback: DogCloudCallback) {
        service.getPicture().enqueue(object : retrofit2.Callback<DogCloud> {
            override fun onResponse(
                call: Call<DogCloud>,
                response: Response<DogCloud>
            ) {
                if (response.isSuccessful) {
                    callback.provide(response.body()!!)
                } else {
                    callback.provideError(serviceUnavailable)
                }
            }

            override fun onFailure(call: Call<DogCloud>, t: Throwable) {
                val error = if (t is UnknownHostException) {
                    noConnection
                } else {
                    serviceUnavailable
                }
                callback.provideError(error)
            }

        })
    }
}