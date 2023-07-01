package com.example.randomdogspicture.presentation

import androidx.annotation.DrawableRes
import com.example.randomdogspicture.model.DataCallback
import com.example.randomdogspicture.model.PictureSelection
import com.example.randomdogspicture.model.Repository

class ViewModel(private val repository: Repository) {

    private var resultUiCallback: ResultUiCallback = ResultUiCallback.Empty()
    private val dataCallback = object : DataCallback {
        override fun provideDog(dogUI: DogUI) {
            dogUI.provide(resultUiCallback)
        }

        override fun provideError(error: Error) {
            resultUiCallback.provideError(error)
        }
    }
    fun getPicture(selection: PictureSelection = PictureSelection.CURRENT) {
        repository.fetch(selection)
    }

    fun chooseFavorites(fromCache: Boolean) {
        repository.chooseFavorites(fromCache)
    }
    fun changeStatus() {
        repository.changeDogPictureStatus(dataCallback)
    }

    fun init(resultUiCallback: ResultUiCallback) {

        this.resultUiCallback = resultUiCallback
        this.repository.init(dataCallback)
    }

    fun clear() {
        resultUiCallback = ResultUiCallback.Empty()
        repository.clear()
    }

}

interface ResultUiCallback {
    fun provideUrl(url: String)
    fun provideError(error: com.example.randomdogspicture.presentation.Error)

    fun provideIconResId(@DrawableRes id: Int)

    class Empty : ResultUiCallback {
        override fun provideUrl(url: String) = Unit
        override fun provideError(error: Error) = Unit
        override fun provideIconResId(id: Int) = Unit
    }
}