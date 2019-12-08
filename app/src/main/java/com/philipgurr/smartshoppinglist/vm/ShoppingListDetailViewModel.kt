package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.AddProductUseCase
import com.philipgurr.smartshoppinglist.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingListDetailViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {
    val shoppingList = MutableLiveData<ShoppingList>()
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = getProductsUseCase.getProducts(shoppingList.value!!.name)
            updateShoppingList()
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCase.add(shoppingList.value!!.name, product)
            loadProducts()
        }
    }

    private fun updateShoppingList() = with(shoppingList.value!!) {
        viewModelScope.launch {
            shoppingList.value = ShoppingList(id, name, created, _products.value!!)
        }
    }

    fun completeProduct(product: Product) {
        viewModelScope.launch {
            val newProduct = Product(product.name, !product.completed)
            insertProduct(newProduct)
        }
    }
}