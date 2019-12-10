package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun getProducts(shoppingListName: String) = withContext(Dispatchers.Default) {
        repository.getProducts(shoppingListName)
    }
}