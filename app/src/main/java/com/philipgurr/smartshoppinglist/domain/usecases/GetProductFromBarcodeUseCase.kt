package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.repository.UpcRepository
import javax.inject.Inject

class GetProductFromBarcodeUseCase @Inject constructor(
    private val repository: UpcRepository
) {
    suspend fun getProduct(code: String) = repository.getProduct(code)
}