package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.AddProductUseCase
import com.philipgurr.smartshoppinglist.domain.usecases.GetListsUseCase
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

    fun insertProduct(name: String) {
        insertProduct(Product(name))
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCase.add(listName, product)
            loadShoppingList()
        }
    }

    fun toggleCompleted(product: Product) {
        viewModelScope.launch {
            val newProduct = Product(product.name, !product.completed)
            insertProduct(newProduct)
        }
    }
}