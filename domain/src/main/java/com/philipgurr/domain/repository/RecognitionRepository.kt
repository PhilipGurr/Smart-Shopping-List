package com.philipgurr.domain.repository

import com.philipgurr.domain.RecognitionImage

interface RecognitionRepository {
    suspend fun recognize(image: RecognitionImage): String
}