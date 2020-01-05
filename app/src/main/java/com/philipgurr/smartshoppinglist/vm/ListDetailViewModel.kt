package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {
    var listName = ""
    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList> = _shoppingList

    fun loadShoppingList() {
        viewModelScope.launch {
            _shoppingList.value = repository.getList(listName)
        }
    }

    fun toggleCompleted(product: Product) {
        viewModelScope.launch {
            val newProduct =
                Product(product.name, !product.completed)
            insertProduct(newProduct) // TODO: Create toggle method in repository for setting product as completed instead of using insert
        }
    }

    private fun insertProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(listName, product)
            loadShoppingList()
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            shoppingList.value?.let {
                repository.deleteProduct(it.id, product)
                loadShoppingList()
            }
        }
    }
}