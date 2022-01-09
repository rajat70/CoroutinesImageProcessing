package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class MainActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_URL = "https://images.pexels.com/photos/1535162/pexels-photo-1535162.jpeg"
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coroutineScope.launch {
            val deferredJob = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }
            val bitmap = deferredJob.await()
            loadImage(bitmap)
        }
    }

    private fun getOriginalBitmap(): Bitmap {
        return URL(IMAGE_URL).openStream().use { it: InputStream? ->
            BitmapFactory.decodeStream(it)
        }
    }

    private fun loadImage(bmp: Bitmap) {
        binding.progressBar.isGone = true
        binding.imageView.setImageBitmap(bmp)
        binding.imageView.isVisible = true
    }
}