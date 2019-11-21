package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.Repository
import javax.inject.Inject

class GetShoppingListsUseCase @Inject constructor(
    private val repository: Repository<ShoppingList>
) {

    suspend fun getShoppingLists() = repository.getAll()
}