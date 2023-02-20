package com.example.randomdogspicture.model

import com.example.randomdogspicture.model.cloud.LinkDogPictureCloud

interface LinkDogPictureCloudCallback {

    fun provide(linkDogPictureCloud: LinkDogPictureCloud)

    fun fail(error: com.example.randomdogspicture.view.Error)
}