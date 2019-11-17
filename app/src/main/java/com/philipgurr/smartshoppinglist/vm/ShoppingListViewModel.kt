package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.GetShoppingListsUseCase
import com.philipgurr.smartshoppinglist.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
    private val shoppingListsUseCase: GetShoppingListsUseCase
) : ViewModel() {

    private val _text = MutableLiveData<List<ShoppingList>>()
    val shoppingLists: LiveData<List<ShoppingList>> = _text

    fun loadShoppingLists() {
        viewModelScope.launch {
            _text.value = shoppingListsUseCase.getShoppingLists()
        }
    }
}