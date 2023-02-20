package com.example.randomdogspicture.view

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.randomdogspicture.MyApplication
import com.example.randomdogspicture.databinding.ActivityMainBinding
import com.example.randomdogspicture.viewmodel.ResultCallback
import com.example.randomdogspicture.viewmodel.ViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = (application as MyApplication).viewModel


        binding.getPictureButton.setOnClickListener {
            viewModel.getPicture(binding.dogImageView)
        }

        binding.prevPictureButton.setOnClickListener {
            vi
        }



        viewModel.init(object : ResultCallback {
            override fun provideSuccess()  = with(binding){
                errorTextView.visibility = View.GONE
                downloadButton.visibility = View.VISIBLE
                favoriteButton.visibility = View.VISIBLE
            }

            override fun provideError(error: Error) = with(binding) {
                errorTextView.run {
                    visibility = View.VISIBLE
                    text = error.message()
                }
                downloadButton.visibility = View.GONE
                favoriteButton.visibility = View.GONE
            }

        })



    }


}