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
    lateinit var shoppingList: ShoppingList
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun loadProducts(shoppingList: ShoppingList) {
        this.shoppingList = shoppingList
        viewModelScope.launch {
            _products.value = getProductsUseCase.getProducts(shoppingList.name)
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCase.add(shoppingList.name, product)
        }
    }
}