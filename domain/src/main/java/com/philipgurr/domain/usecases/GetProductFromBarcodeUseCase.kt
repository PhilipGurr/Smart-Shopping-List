package com.philipgurr.domain.usecases

import com.philipgurr.domain.repository.UpcRepository
import javax.inject.Inject

class GetProductFromBarcodeUseCase @Inject constructor(
    private val repository: UpcRepository
) {
    suspend fun getProduct(code: String) = repository.getProduct(code)
}