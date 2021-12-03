package com.example.resistorvision

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class IntroduceActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce)
    }

    override fun onResume() {
        super.onResume()
        val image = findViewById<ImageView>(R.id.introduce_sample_img)

        val bitmapImage = image.drawable.toBitmap()
        val grayScaleImage = toGrayScale(bitmapImage)
        image.setImageBitmap(grayScaleImage)
    }

    private fun toGrayScale(bitmap: Bitmap):Bitmap {
        var matImage = Mat()
        var resultMat = Mat()
        OpenCVLoader.initDebug()
        Utils.bitmapToMat(bitmap, matImage)
        Imgproc.cvtColor(matImage, resultMat, Imgproc.COLOR_BGR2GRAY)

        var resultImage: Bitmap? = null
        Utils.matToBitmap(resultMat, resultImage)
        return resultImage!!
    }
}