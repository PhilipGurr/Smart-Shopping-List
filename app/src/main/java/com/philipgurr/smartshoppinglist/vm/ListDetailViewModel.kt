package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.usecases.AddProductUseCase
import com.philipgurr.domain.usecases.GetListsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {
    var listName = ""
    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList> = _shoppingList

    fun loadShoppingList() {
        viewModelScope.launch {
            _shoppingList.value = getListsUseCase.getShoppingList(listName)
        }
    }

    fun toggleCompleted(product: Product) {
        viewModelScope.launch {
            val newProduct =
                Product(product.name, !product.completed)
            insertProduct(newProduct) // TODO: Create use case for setting product as completed instead of using insert
        }
    }

    private fun insertProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCase.add(listName, product)
            loadShoppingList()
        }
    }
}