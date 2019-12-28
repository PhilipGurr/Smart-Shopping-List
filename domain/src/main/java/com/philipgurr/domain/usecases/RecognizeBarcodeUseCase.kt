package com.philipgurr.domain.usecases

import com.philipgurr.domain.RecognitionImage
import com.philipgurr.domain.repository.RecognitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecognizeBarcodeUseCase @Inject constructor(
    private val recognitionRepository: RecognitionRepository
) {
    suspend fun recognize(image: RecognitionImage): String = withContext(Dispatchers.IO) {
        recognitionRepository.recognize(image)
    }
}