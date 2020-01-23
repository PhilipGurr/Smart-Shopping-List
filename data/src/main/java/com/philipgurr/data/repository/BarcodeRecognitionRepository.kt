package com.philipgurr.data.repository

import android.graphics.Rect
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.philipgurr.data.util.await
import com.philipgurr.domain.barcode.Barcode
import com.philipgurr.domain.barcode.BarcodeBoundingBox
import com.philipgurr.domain.barcode.RecognitionImage
import com.philipgurr.domain.repository.RecognitionRepository
import javax.inject.Inject

class BarcodeRecognitionRepository @Inject constructor() : RecognitionRepository {
    override suspend fun recognize(image: RecognitionImage): Barcode? {
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

        val firstBarcode = barcodes.firstOrNull() ?: return null
        val rawValue = firstBarcode.rawValue ?: return null
        val boundingBox = firstBarcode.boundingBox ?: return null

        return Barcode(rawValue, boundingBox.toBarcodeBoundingBox())
    }

    private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    private fun Rect.toBarcodeBoundingBox() = BarcodeBoundingBox(left, top, right, bottom)

    fun getBarcodeDetectorOptions() = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_UPC_A,
            FirebaseVisionBarcode.FORMAT_UPC_E
        )
        .build()
}