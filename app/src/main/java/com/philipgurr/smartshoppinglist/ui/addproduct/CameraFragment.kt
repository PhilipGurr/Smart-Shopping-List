package com.philipgurr.smartshoppinglist.ui.addproduct

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
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
import com.philipgurr.domain.barcode.Barcode
import com.philipgurr.domain.barcode.RecognitionImage
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.FragmentCameraBinding
import com.philipgurr.smartshoppinglist.util.extensions.YUV_420_888toNV21
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
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
        ViewModelProviders.of(activity!!, factory).get(ListDetailViewModel::class.java)
    }
    private val imageAnalysisExecutor = Executors.newSingleThreadExecutor()
    private lateinit var cameraPreview: Preview
    private var isResultViewUp = false
    private var isNotFoundViewUp = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parseArguments()
        val binding: FragmentCameraBinding = setupDataBinding(inflater, container)
        return binding.root
    }

    private fun parseArguments() {
        arguments?.let { bundle ->
            val safeArgs = CameraFragmentArgs.fromBundle(bundle)
            viewModel.setShoppingList(safeArgs.shoppingList)
        }
    }

    private fun setupDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCameraBinding {
        val binding: FragmentCameraBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_camera,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFinder.post { startCamera() }
        setupClickListeners()

        viewModel.resetBarcodeRecognition()
        viewModel.getRecognizedBarcode().observe(this, Observer { barcode ->
            val boundingBox = getTranslatedBoundingBoxFromBarcode(barcode)
            if (rectOverlapsCenter(boundingBox)) {
                viewModel.disableRecognizer()
                barcode_frame.animateBarcodeFound()
                viewModel.getProduct(barcode)
            }
        })
        viewModel.getRecognizedProduct().observe(this, Observer { _ ->
            // Parameter omitted because the product data is shown using data binding
            if (noResultViewsVisible()) {
                slideResultViewUp()
            }
        })
        viewModel.getProductNotFound().observe(this, Observer { errorMessage ->
            if (noResultViewsVisible()) {
                productNotFound(errorMessage)
            }
        })
    }

    private fun setupClickListeners() {
        add_product.onClick {
            slideResultViewDown()
            viewModel.insertCurrentProduct()
            viewModel.enableRecognizer()
        }
        viewFinder.onClick {
            if (isResultViewUp) {
                slideResultViewDown()
                viewModel.enableRecognizer()
            }
        }
        flash_button.onClick {
            if (flash_button.isSelected) {
                flash_button.isSelected = false
                cameraPreview.enableTorch(false)
            } else {
                flash_button.isSelected = true
                cameraPreview.enableTorch(true)
            }
        }
    }

    private fun getTranslatedBoundingBoxFromBarcode(barcode: Barcode): RectF {
        val boundingBox = barcode.boundingBox
        val boundingRect = with(boundingBox) {
            Rect(left, top, right, bottom)
        }
        return barcode_frame.translateRect(boundingRect)
    }

    private fun rectOverlapsCenter(rect: RectF) =
        rect.contains(viewFinder.width / 2f, viewFinder.height / 2f)

    private fun noResultViewsVisible() = !isResultViewUp && !isNotFoundViewUp

    private fun startCamera() = runWithPermissions(Manifest.permission.CAMERA) {
        val viewFinderSize = Size(viewFinder.width, viewFinder.height)
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(viewFinderSize)
        }.build()

        cameraPreview = Preview(previewConfig)
        cameraPreview.setOnPreviewOutputUpdateListener { previewOutput ->
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = previewOutput.surfaceTexture
            compensateOrientationInViewFinder()
        }

        CameraX.bindToLifecycle(this, setupImageAnalyser(), cameraPreview)
    }

    private fun setupImageAnalyser(): ImageAnalysis {
        val config = ImageAnalysisConfig.Builder()
            .setTargetResolution(Size(1920, 1080))
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .build()
        val analysis = ImageAnalysis(config)
        analysis.setAnalyzer(
            imageAnalysisExecutor,
            ImageAnalysis.Analyzer { imageProxy: ImageProxy, rotationDegrees: Int ->
                if (barcode_frame != null) {
                    barcode_frame.setPreviewSize(imageProxy.width, imageProxy.height)
                }
                val recognitionImage =
                    RecognitionImage(
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
        with(result_card_view) {
            visibility = View.VISIBLE
            val animation = TranslateAnimation(0f, 0f, height.toFloat(), 0f)
            animation.duration = 300
            animation.fillAfter = true
            startAnimation(animation)
        }
    }

    private fun slideResultViewDown() {
        isResultViewUp = false
        with(result_card_view) {
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
            {
                isNotFoundViewUp = false
                viewModel.enableRecognizer()
            },
            SNACKBAR_DURATION.toLong()
        )
    }
}
