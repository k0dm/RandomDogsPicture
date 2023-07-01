package com.example.randomdogspicture.presentation

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
import com.example.randomdogspicture.R
import com.example.randomdogspicture.databinding.ActivityMainBinding
import com.example.randomdogspicture.model.PictureSelection


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: ActivityMainBinding
    private var fromCache = false

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
            viewModel.changeStatus()
        }

        binding.savedImageButton.setOnClickListener {
            fromCache = !fromCache
            viewModel.chooseFavorites(fromCache)
            binding.apply {
                if (fromCache) {
                    viewModel.getPicture()
                    getPictureButton.visibility = View.GONE
                    nextPictureButton.visibility = View.VISIBLE
                    prevPictureButton.visibility = View.VISIBLE

                } else {
                    getPictureButton.visibility = View.VISIBLE
                    nextPictureButton.visibility = View.GONE
                    prevPictureButton.visibility = View.GONE
                }

            }
        }

        binding.nextPictureButton.setOnClickListener {
            viewModel.getPicture(PictureSelection.NEXT)
        }

        binding.prevPictureButton.setOnClickListener {
            viewModel.getPicture(PictureSelection.PREV)

        }

        viewModel.init(object : ResultUiCallback {
            override fun provideUrl(url: String) = with(binding) {

                Glide.with(this@MainActivity)
                    .load(url)
                    .placeholder(R.drawable.baseline_cloud_download_100)
                    .error(R.drawable.baseline_error_100)
                    .into(dogImageView)

                downloadButton.isActivated = true
                favoriteButton.isActivated = true
                savedImageButton.isActivated = true
                errorTextView.visibility = View.GONE
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

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }
}