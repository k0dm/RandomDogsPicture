package com.example.randomdogspicture.model

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.randomdogspicture.viewmodel.ResultCallback

class PictureRepository : Repository {

    private var resultCallback: ResultCallback = ResultCallback.Empty()



    override fun getPicture(imageView: ImageView) {


    }




    override fun init(resultCallback: ResultCallback) {
        this.resultCallback = resultCallback
    }

    override fun clear() {
        resultCallback = ResultCallback.Empty()
    }



}