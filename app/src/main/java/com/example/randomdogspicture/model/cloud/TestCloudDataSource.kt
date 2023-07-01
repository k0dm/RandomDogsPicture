package com.example.randomdogspicture.model.cloud

class TestCloudDataSource : CloudDataSource {

    private val urls = listOf(
//        "https://images.dog.ceo/breeds/retriever-flatcoated/n02099267_1272.jpg",
//        "https://images.dog.ceo/breeds/borzoi/n02090622_10343.jpg",
        "https://images.dog.ceo/breeds/puggle/IMG_095312.jpg",
        "https://images.dog.ceo/breeds/affenpinscher/n02110627_3144.jpg",
        "https://images.dog.ceo/breeds/terrier-toy/n02087046_4906.jpg"

    )
    private var index = 0

    override fun fetch(callback: DogCloudCallback) {
        index++
        if (index >= urls.size) {
            index %= urls.size
        }
        callback.provide(
            DogCloud(urls[index])
        )
    }


}