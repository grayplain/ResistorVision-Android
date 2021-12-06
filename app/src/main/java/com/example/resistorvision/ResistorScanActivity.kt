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

    private var processor = ResistorColorDetector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseLoaderCallback = object : BaseLoaderCallback(this) {
            override fun onManagerConnected(status: Int) {
                when (status) {
                    LoaderCallbackInterface.SUCCESS -> {
                        System.loadLibrary("opencv_java4")
                        openCvCameraView.setCameraPermissionGranted()
                        openCvCameraView.enableView()
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
            return processor.processFrame(it)
        }
        return Mat()
    }


}
