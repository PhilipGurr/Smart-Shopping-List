package com.philipgurr.domain.usecases

import com.philipgurr.domain.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCompletedListsUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) : GetListsUseCase(repository) {

    suspend fun getCompletedLists() = withContext(Dispatchers.IO) {
        getShoppingLists().filter { shoppingList ->
            shoppingList.completedProducts().size == shoppingList.products.size
        }
    }
}