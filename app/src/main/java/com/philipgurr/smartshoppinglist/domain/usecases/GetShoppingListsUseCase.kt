package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetShoppingListsUseCase @Inject constructor(
    private val repository: Repository<ShoppingList>
) {

    suspend fun getShoppingLists() = withContext(Dispatchers.Default) {
        repository.getAll().sortedBy { it.created }
    }
}