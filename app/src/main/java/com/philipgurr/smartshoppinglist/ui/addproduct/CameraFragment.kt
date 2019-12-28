package com.philipgurr.smartshoppinglist.ui.addproduct


import android.Manifest
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.philipgurr.domain.RecognitionImage
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.util.extensions.YUV_420_888toNV21
import com.philipgurr.smartshoppinglist.vm.AddProductViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_camera.*
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(AddProductViewModel::class.java)
    }
    private val imageAnalysisExecutor = Executors.newSingleThreadExecutor()
    private lateinit var useCase: UseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder.post { startCamera() }

        viewModel.recognizedProduct.observe(this, Observer {
            addProduct(it.name)
        })
        viewModel.barcodeNotFound.observe(this, Observer {
            productNotFound(it)
        })
    }

    private fun startCamera() = runWithPermissions(Manifest.permission.CAMERA) {
        val viewFinderSize = Size(viewFinder.width, viewFinder.height)
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(viewFinderSize)
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            compensateOrientationInViewFinder()
        }

        CameraX.bindToLifecycle(this, setupImageAnalyser(), preview)
    }

    private fun setupImageAnalyser(): ImageAnalysis {
        val config = ImageAnalysisConfig.Builder()
            .setTargetResolution(Size(1280, 720))
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .build()
        val analysis = ImageAnalysis(config)
        analysis.setAnalyzer(
            imageAnalysisExecutor,
            ImageAnalysis.Analyzer { imageProxy: ImageProxy, rotationDegrees: Int ->
                val recognitionImage = RecognitionImage(
                    imageProxy.width,
                    imageProxy.height,
                    YUV_420_888toNV21(imageProxy),
                    rotationDegrees
                )
                viewModel.recognizeBarcode(recognitionImage)
            })
        return analysis
    }

    private fun compensateOrientationInViewFinder() {
        val matrix = Matrix()

        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        viewFinder.setTransform(matrix)
    }

    private fun addProduct(productName: String) {
        alert("Add Product") {
            customView {
                val name = editText { setText(productName) }
                okButton {
                    val text = name.text.toString()
                    viewModel.recognizerRunning = false
                    Log.d("CameraFragment", text)
                    viewModel.insertProduct(text)
                }
                cancelButton { viewModel.recognizerRunning = false }
            }
        }.show()
    }

    private fun productNotFound(message: String) {
        Log.d("CameraFragment", message)
        alert(message) {
            okButton { viewModel.recognizerRunning = false }
        }.show()
    }
}
