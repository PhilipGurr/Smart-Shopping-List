package com.philipgurr.data.repository

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.philipgurr.data.util.await
import com.philipgurr.domain.RecognitionImage
import com.philipgurr.domain.repository.RecognitionRepository
import javax.inject.Inject

class BarcodeRecognitionRepository @Inject constructor() : RecognitionRepository {
    override suspend fun recognize(image: RecognitionImage): String {
        val metadata = with(image) {
            FirebaseVisionImageMetadata.Builder()
                .setWidth(width)
                .setHeight(height)
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setRotation(degreesToFirebaseRotation(rotation))
                .build()
        }
        val firebaseImage = FirebaseVisionImage.fromByteArray(image.buffer, metadata)

        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        val barcodes = detector.detectInImage(firebaseImage).await()
        return barcodes.firstOrNull()?.rawValue ?: ""
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    fun getBarcodeDetectorOptions() = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_UPC_A,
            FirebaseVisionBarcode.FORMAT_UPC_E
        )
        .build()
}