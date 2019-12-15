package com.philipgurr.smartshoppinglist.domain.usecases

import android.graphics.Bitmap
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.util.extensions.rotate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecognizeBarcodeUseCase @Inject constructor(
    private val inputMethod: ProductInputMethod<FirebaseVisionImage, String>
) {
    suspend fun recognize(bitmap: Bitmap, rotation: Int): String = withContext(Dispatchers.IO) {
        val firebaseImage = FirebaseVisionImage.fromBitmap(bitmap.rotate(rotation.toFloat()))
        bitmap.recycle()
        inputMethod.process(firebaseImage)
    }
}