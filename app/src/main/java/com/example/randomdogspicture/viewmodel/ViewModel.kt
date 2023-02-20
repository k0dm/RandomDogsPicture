package com.example.randomdogspicture.viewmodel

import android.widget.ImageView
import com.example.randomdogspicture.model.Repository
import com.example.randomdogspicture.view.Error

class ViewModel(private val repository: Repository) {

    private var resultCallback: ResultCallback = ResultCallback.Empty()


    fun getPicture(imageView: ImageView) {
        repository.getPicture(imageView)
    }

    fun init(resultCallback: ResultCallback) {
       // this.resultCallback = resultCallback
        repository.init(resultCallback)
    }

    fun clear() {
        resultCallback = ResultCallback.Empty()
        repository.clear()
    }

}

interface ResultCallback {
    fun provideSuccess()
    fun provideError(error: com.example.randomdogspicture.view.Error)

    class Empty : ResultCallback {
        override fun provideSuccess() = Unit
        override fun provideError(error: Error) = Unit
    }
}