package com.philipgurr.smartshoppinglist.domain.usecases

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecognizeBarcodeUseCase @Inject constructor() {
    suspend fun recognize(image: Bitmap): String = withContext(Dispatchers.IO) {
        ""
    }
}