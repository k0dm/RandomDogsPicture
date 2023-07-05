package com.example.randomdogspicture.presentation

import com.example.randomdogspicture.model.BitmapCallback
import com.example.randomdogspicture.model.DogUiCallback
import com.example.randomdogspicture.model.PictureSelection
import com.example.randomdogspicture.model.Repository
import com.example.randomdogspicture.model.DataUiCallback
import com.example.randomdogspicture.model.ResultCallback

class ViewModel(private val repository: Repository) {

    private var dataUiCallback: DataUiCallback = DataUiCallback.Empty()
    private val dogUiCallback = object : DogUiCallback {
        override fun provideDog(dogUI: DogUI) {
            dogUI.provide(dataUiCallback)
        }

        override fun provideError(error: Error) {
            dataUiCallback.provideError(error)
        }
    }

    fun getPicture(selection: PictureSelection = PictureSelection.CURRENT) {
        repository.fetch(selection)
    }

    fun chooseFavorites(fromCache: Boolean) {
        repository.chooseFavorites(fromCache)
    }

    fun changeStatus() {
        repository.changeDogPictureStatus(dogUiCallback)
    }

    fun init(dataUiCallback: DataUiCallback) {

        this.dataUiCallback = dataUiCallback
        this.repository.init(dogUiCallback)
    }

    fun clear() {
        dataUiCallback = DataUiCallback.Empty()
        repository.clear()
    }

    fun downloadPicture(resultCallback: ResultCallback) {
        Thread {
            repository.downloadPicture(resultCallback)
        }.start()
    }

}

