package com.philipgurr.smartshoppinglist.ui.addproduct

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.camera.core.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.philipgurr.domain.RecognitionImage
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.FragmentCameraBinding
import com.philipgurr.smartshoppinglist.util.extensions.YUV_420_888toNV21
import com.philipgurr.smartshoppinglist.vm.AddProductViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_camera.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.concurrent.Executors
import javax.inject.Inject

private const val SNACKBAR_DURATION = 2750

class CameraFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(AddProductViewModel::class.java)
    }
    private val imageAnalysisExecutor = Executors.newSingleThreadExecutor()
    private var isResultViewUp = false
    private var isNotFoundViewUp = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCameraBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_camera,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder.post { startCamera() }
        add_product.onClick {
            slideResultViewDown()
            viewModel.insertCurrentProduct()
        }
        viewFinder.onClick {
            if (isResultViewUp) {
                slideResultViewDown()
            }
        }

        viewModel.resetBarcodeRecognition()
        viewModel.getRecognizedProduct().observe(this, Observer {
            if (!isResultViewUp) {
                slideResultViewUp()
            }
        })
        viewModel.getBarcodeNotFound().observe(this, Observer {
            if (!isNotFoundViewUp) {
                productNotFound(it)
            }
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

    private fun slideResultViewUp() {
        isResultViewUp = true
        with(result_carde_view) {
            visibility = View.VISIBLE
            val animation = TranslateAnimation(0f, 0f, height.toFloat(), 0f)
            animation.duration = 300
            animation.fillAfter = true
            startAnimation(animation)
        }
    }

    private fun slideResultViewDown() {
        isResultViewUp = false
        with(result_carde_view) {
            visibility = View.VISIBLE
            val animation = TranslateAnimation(0f, 0f, 0f, height.toFloat())
            animation.duration = 300
            animation.fillAfter = true
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    visibility = View.GONE
                }

                override fun onAnimationStart(p0: Animation?) {}
            })
            startAnimation(animation)
        }
    }

    @SuppressLint("WrongConstant")
    private fun productNotFound(message: String) {
        isNotFoundViewUp = true
        Snackbar.make(view!!, message, SNACKBAR_DURATION).show()
        view!!.postDelayed(
            { isNotFoundViewUp = false },
            SNACKBAR_DURATION.toLong()
        )
    }
}
