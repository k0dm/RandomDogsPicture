package com.example.randomdogspicture.presentation

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.randomdogspicture.MyApplication
import com.example.randomdogspicture.R
import com.example.randomdogspicture.databinding.ActivityMainBinding
import com.example.randomdogspicture.model.BitmapCallback
import com.example.randomdogspicture.model.PictureSelection
import com.example.randomdogspicture.model.DataUiCallback
import com.example.randomdogspicture.model.ResultCallback


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: ActivityMainBinding
    private var fromCache = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                writePermissionGranted =
                    permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE]
                        ?: writePermissionGranted
            }


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

        binding.downloadButton.setOnClickListener {


            val hasWritePermission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
            if (!hasWritePermission) {  //request the permission
                permissionsLauncher.launch(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            }
            viewModel.downloadPicture(object : ResultCallback {

                override fun provideSuccess() = runOnUiThread {
                    Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
                }

                override fun provideError(error: Error) = runOnUiThread {
                    Toast.makeText(this@MainActivity, error.message(), Toast.LENGTH_SHORT).show()
                }

            })

        }

        viewModel.init(object : DataUiCallback {
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


//    fun loadImageAndSaveToStorage(context: Context, imageUrl: String, fileName: String) {
//        Glide.with(context)
//            .asBitmap()
//            .load(imageUrl)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    saveBitmapToStorage(context, resource, fileName)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // Do nothing
//                }
//            })
//    }
//
//    private fun saveBitmapToStorage(context: Context, bitmap: Bitmap, fileName: String) {
//        val fileOutputStream: FileOutputStream
//        try {
//            val directory = context.getExternalFilesDir(null) // Get the directory for saving images in external storage
//            if (directory != null) {
//                val file = File(directory, fileName)
//                fileOutputStream = FileOutputStream(file)
//
//                // Compress the bitmap to PNG format and save it to the file
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
//                fileOutputStream.close()
//
//                // The image is now saved to local storage
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }
}