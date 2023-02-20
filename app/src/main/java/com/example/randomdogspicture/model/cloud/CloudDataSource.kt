package com.example.randomdogspicture.model.cloud

import com.example.randomdogspicture.model.LinkDogPictureCloudCallback

interface CloudDataSource {
    fun getPicture(callback: LinkDogPictureCloudCallback)
}