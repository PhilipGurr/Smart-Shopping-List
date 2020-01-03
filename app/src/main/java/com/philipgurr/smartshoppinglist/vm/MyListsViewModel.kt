package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.data.database.UserManager
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.usecases.AddListUseCase
import com.philipgurr.domain.usecases.DeleteListUseCase
import com.philipgurr.domain.usecases.GetListsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyListsViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val addListUseCase: AddListUseCase,
    private val deleteListUseCase: DeleteListUseCase,
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
            _shoppingLists.value = getListsUseCase.getShoppingLists()
        }
    }

    fun createShoppingList(name: String) {
        viewModelScope.launch {
            addListUseCase.addList(name)
            loadShoppingLists()
        }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            deleteListUseCase.delete(name)
        }
    }
}