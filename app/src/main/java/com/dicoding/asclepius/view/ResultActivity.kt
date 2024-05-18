package com.dicoding.asclepius.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriString = intent.getStringExtra("uri")
        val uri = Uri.parse(uriString)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.

        binding.resultImage.setImageURI(uri)
        imageClassifierHelper = ImageClassifierHelper(context = this)
        classifyImage(uri)
    }

    @SuppressLint("SetTextI18n")
    private fun classifyImage(uri: Uri) {
        val results = imageClassifierHelper.classifyStaticImage(uri)
        if (results != null && results.isNotEmpty()) {
            val clfResult = results[0].categories[0]
            binding.resultText.text = "${clfResult.label} "+ NumberFormat.getPercentInstance()
                .format(clfResult.score).trim()
        } else {
            binding.resultText.text = "Failed to classify picture"
        }
    }
}