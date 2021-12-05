package com.example.resistorvision

import android.os.Bundle
import android.view.View
import org.opencv.android.*
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.core.Scalar




class ResistorScanActivity : CameraActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
    private lateinit var openCvCameraView: JavaCamera2ViewPlus
    private lateinit var baseLoaderCallback: BaseLoaderCallback

    private lateinit var memoryTestMat: Mat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseLoaderCallback = object : BaseLoaderCallback(this) {
            override fun onManagerConnected(status: Int) {
                when (status) {
                    LoaderCallbackInterface.SUCCESS -> {
                        System.loadLibrary("opencv_java4")
                        openCvCameraView.setCameraPermissionGranted()
                        openCvCameraView.enableView()
                        memoryTestMat = Mat()
                    }
                    else -> super.onManagerConnected(status)
                }
            }
        }
        setContentView(R.layout.activity_resistor_scan)
        openCvCameraView= findViewById(R.id.resistor_scan_camera_view)
        openCvCameraView.visibility = View.VISIBLE
        openCvCameraView.setCvCameraViewListener(this)
    }

    override fun onResume() {
        super.onResume()
        if(OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, baseLoaderCallback)
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {

    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        inputFrame?.let {
//            return it.rgba()
//            return combineMat(it.rgba(), detectColor(scanResistorROI(it.rgba())))
//            return combineMat(it.rgba(), it.gray())

            return it.rgba()
        }
        return Mat()
    }

    private fun combineMat(mainMat: Mat, subMat: Mat): Mat {
        var newMainMat = mainMat
        var newSubMat = Mat()
        Imgproc.resize(subMat, newSubMat, newMainMat.size())

        Core.add(newMainMat, newSubMat, newMainMat)
        return newMainMat
    }

    //FIXME: メモリーリーク大量に発生
    private fun scanResistorROI(originalMat: Mat) {
        val rowStart = 200
        val rowEnd = originalMat.rows() - 200
        val colStart = originalMat.cols() / 4
        val colEnd = originalMat.cols() * 3 / 4
        originalMat.submat(rowStart, rowEnd, colStart, colEnd).copyTo(memoryTestMat)
    }

    //FIXME: メモリーリーク大量に発生
    private fun detectColor(originalMat: Mat): Mat {
        var newMat = originalMat
        Imgproc.cvtColor(newMat, newMat, Imgproc.COLOR_RGBA2BGR)
        Imgproc.cvtColor(newMat, newMat, Imgproc.COLOR_BGR2HSV)
        Core.inRange(newMat, Scalar(0.0, 38.0, 89.0), Scalar(20.0, 192.0, 243.0), newMat)
        Imgproc.cvtColor(newMat, newMat, Imgproc.COLOR_GRAY2BGRA)
        return newMat
    }
}
