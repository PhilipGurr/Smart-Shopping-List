package com.philipgurr.smartshoppinglist.productinput.processors

import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.util.extensions.await
import javax.inject.Inject

class BarcodeInputMethod @Inject constructor() : ProductInputMethod<FirebaseVisionImage, String> {
    override suspend fun process(value: FirebaseVisionImage): String {
        //val options = getBarcodeDetectorOptions()
        val detector = FirebaseVision.getInstance().visionBarcodeDetector
        val barcodes = detector.detectInImage(value).await()
        return barcodes.firstOrNull()?.rawValue ?: ""
    }

    fun getBarcodeDetectorOptions() = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
            FirebaseVisionBarcode.FORMAT_UPC_A,
            FirebaseVisionBarcode.FORMAT_UPC_E
        )
        .build()
}