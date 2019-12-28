package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.usecases.AddListUseCase
import com.philipgurr.domain.usecases.GetListsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyListsViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val addListUseCase: AddListUseCase
) : ViewModel() {

    private val _text = MutableLiveData<List<ShoppingList>>()
    val shoppingLists: LiveData<List<ShoppingList>> = _text

    fun loadShoppingLists() {
        viewModelScope.launch {
            _text.value = getListsUseCase.getShoppingLists()
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            addListUseCase.addList(name)
            loadShoppingLists()
        }
    }
}