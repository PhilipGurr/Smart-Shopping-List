package com.philipgurr.domain.usecases

import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.util.toId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend fun delete(name: String) = withContext(Dispatchers.Default) {
        repository.deleteList(name.toId())
    }
}