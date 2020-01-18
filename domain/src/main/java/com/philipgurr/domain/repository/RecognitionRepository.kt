package com.philipgurr.domain.repository

import com.philipgurr.domain.RecognitionImage
import com.philipgurr.domain.barcode.Barcode

interface RecognitionRepository {
    suspend fun recognize(image: RecognitionImage): Barcode?
}