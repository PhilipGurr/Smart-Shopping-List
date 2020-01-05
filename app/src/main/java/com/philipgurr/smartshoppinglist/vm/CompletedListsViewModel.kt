package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompletedListsViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {
    private val _completedLists = MutableLiveData<List<ShoppingList>>()
    val completedLists: LiveData<List<ShoppingList>> = _completedLists

    fun loadShoppingLists() {
        viewModelScope.launch {
            _completedLists.value = repository.getAllLists().filter { shoppingList ->
                shoppingList.completedProducts().size == shoppingList.products.size
            }
        }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            repository.deleteList(name)
        }
    }
}