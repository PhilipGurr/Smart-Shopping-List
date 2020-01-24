package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.data.database.UserManager
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.util.toId
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyListsViewModel @Inject constructor(
    private val repository: ShoppingListRepository,
    userManager: UserManager
) : ViewModel() {
    private var internalLists = mutableListOf<ShoppingList>()
    private val _shoppingLists = MutableLiveData<List<ShoppingList>>()
    val shoppingLists: LiveData<List<ShoppingList>> = _shoppingLists

    init {
        userManager.addOnUserChangedListener {
            loadShoppingLists()
        }
    }

    fun loadShoppingLists() {
        viewModelScope.launch {
            internalLists = repository.getNotCompletedLists().toMutableList()
            _shoppingLists.value = internalLists
        }
    }

    fun searchShoppingLists(query: String) {
        if (query.isEmpty()) {
            _shoppingLists.value = internalLists
        }
        _shoppingLists.value = internalLists.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            val newList = ShoppingList(name.toId(), name)
            internalLists.add(newList)
            repository.addList(newList)
            loadShoppingLists()
        }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            val listToDelete = internalLists.first { it.id == name.toId() }
            internalLists.remove(listToDelete)
            repository.deleteList(name.toId())
            loadShoppingLists()
        }
    }
}