package com.philipgurr.domain.repository

import com.philipgurr.domain.barcode.Barcode
import com.philipgurr.domain.barcode.RecognitionImage

interface RecognitionRepository {
    suspend fun recognize(image: RecognitionImage): Barcode?
}