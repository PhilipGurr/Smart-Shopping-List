package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.util.toId
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompletedListsViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {
    private var internalLists = mutableListOf<ShoppingList>()
    private val _completedLists = MutableLiveData<List<ShoppingList>>()
    val completedLists: LiveData<List<ShoppingList>> = _completedLists

    fun loadShoppingLists() {
        viewModelScope.launch {
            internalLists = repository.getCompletedLists().toMutableList()
            _completedLists.value = internalLists
        }
    }

    fun searchShoppingLists(query: String) {
        if (query.isEmpty()) {
            _completedLists.value = internalLists
        }
        _completedLists.value = internalLists.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            val listToDelete = internalLists.first { it.id == name.toId() }
            internalLists.remove(listToDelete)
            repository.deleteList(name.toId())
        }
    }
}