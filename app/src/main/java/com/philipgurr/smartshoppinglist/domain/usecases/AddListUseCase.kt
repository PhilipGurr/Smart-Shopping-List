package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import com.philipgurr.smartshoppinglist.util.extensions.toId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class AddListUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {

    suspend fun addList(name: String) {
        withContext(Dispatchers.Default) {
            val newList = ShoppingList(
                name.toId(),
                name,
                Date(),
                listOf()
            )
            repository.addList(newList)
        }
    }
}