package com.philipgurr.domain.usecases

import com.philipgurr.domain.Product
import com.philipgurr.domain.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    private suspend fun deleteProduct(product: Product) = withContext(Dispatchers.Default) {
        repository.deleteProduct(product)
    }
}