package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {

    suspend fun getShoppingList(name: String) = withContext(Dispatchers.Default) {
        repository.getList(name)
    }

    suspend fun getShoppingLists() = withContext(Dispatchers.Default) {
        repository.getAllLists().sortedBy { it.created }
    }
}