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
    private val _shoppingLists = MutableLiveData<List<ShoppingList>>()
    val shoppingLists: LiveData<List<ShoppingList>> = _shoppingLists

    init {
        userManager.addOnUserChangedListener {
            loadShoppingLists()
        }
    }

    fun loadShoppingLists() {
        viewModelScope.launch {
            _shoppingLists.value = repository.getAllLists()
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            repository.addList(ShoppingList(name.toId(), name))
            loadShoppingLists()
        }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            repository.deleteList(name.toId())
        }
    }
}