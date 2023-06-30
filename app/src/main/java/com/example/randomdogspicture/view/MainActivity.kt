package com.example.randomdogspicture.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.randomdogspicture.MyApplication
import com.example.randomdogspicture.R
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
            binding.apply {
                downloadButton.isActivated = false
                favoriteButton.isActivated = false
                savedImageButton.isActivated = false
            }

            viewModel.getPicture()
        }

        binding.favoriteButton.setOnClickListener {
            // viewModel.changeStatus()
        }

        binding.prevPictureButton.setOnClickListener {

        }



        viewModel.init(object : ResultCallback {
            override fun provideUrl(url: String) = with(binding) {

                Glide.with(this@MainActivity)
                    .load(url)
                    .placeholder(R.drawable.baseline_cloud_download_100)
                    .into(dogImageView)

                errorTextView.visibility = View.GONE
                downloadButton.isActivated = true
                favoriteButton.isActivated = true
                savedImageButton.isActivated = true

            }

            override fun provideError(error: Error) = with(binding) {
                errorTextView.run {
                    visibility = View.VISIBLE
                    text = error.message()
                }
                downloadButton.isActivated = false
                favoriteButton.isActivated = false
                savedImageButton.isActivated = true

            }

            override fun provideIconResId(id: Int) = with(binding) {
                favoriteButton.setImageResource(id)
            }

        })


    }


}