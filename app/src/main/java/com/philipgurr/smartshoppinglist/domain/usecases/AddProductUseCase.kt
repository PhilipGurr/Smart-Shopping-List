package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend fun add(shoppingListName: String, product: Product) = withContext(Dispatchers.Default) {
        repository.addProduct(shoppingListName, product)
    }
}