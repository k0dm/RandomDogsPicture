package com.example.randomdogspicture.viewmodel

import android.graphics.drawable.Drawable
import android.os.DeadObjectException
import androidx.annotation.DrawableRes
import com.example.randomdogspicture.model.DataCallback
import com.example.randomdogspicture.model.Repository
import com.example.randomdogspicture.view.DogUI
import com.example.randomdogspicture.view.Error

class ViewModel(private val repository: Repository) {

    private var resultCallback: ResultCallback = ResultCallback.Empty()


    fun getPicture() {
        repository.getPicture()
    }

    fun init(resultCallback: ResultCallback) {
        this.repository.init(object : DataCallback {
            override fun provideDog(dogUI: DogUI) {
                dogUI.provide(resultCallback)
            }

            override fun provideError(error: Error) {
                resultCallback.provideError(error)
            }
        })

    }

    fun clear() {
        resultCallback = ResultCallback.Empty()
        repository.clear()
    }

}

interface ResultCallback {
    fun provideUrl(url: String)
    fun provideError(error: com.example.randomdogspicture.view.Error)

    fun provideIconResId(@DrawableRes id: Int)

    class Empty : ResultCallback {
        override fun provideUrl(url: String) = Unit
        override fun provideError(error: Error) = Unit
        override fun provideIconResId(id: Int) = Unit
    }
}