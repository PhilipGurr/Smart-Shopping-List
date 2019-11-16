package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.GetShoppingListsUseCase
import com.philipgurr.smartshoppinglist.repository.Repository
import javax.inject.Inject

class ShoppingListViewModel @Inject constructor(
    private val shoppingListsUseCase: GetShoppingListsUseCase
) : ViewModel() {

    private val _text = MutableLiveData<List<ShoppingList>>()
    val shoppingList: LiveData<List<ShoppingList>> = _text

    fun loadShoppingLists() {
        _text.value = shoppingListsUseCase.getShoppingLists()
    }
}