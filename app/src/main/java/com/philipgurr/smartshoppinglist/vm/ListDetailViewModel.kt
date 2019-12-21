package com.philipgurr.smartshoppinglist.vm

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.data.api.BarcodeNotFoundException
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.AddProductUseCase
import com.philipgurr.smartshoppinglist.domain.usecases.GetListsUseCase
import com.philipgurr.smartshoppinglist.domain.usecases.GetProductFromBarcodeUseCase
import com.philipgurr.smartshoppinglist.domain.usecases.RecognizeBarcodeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val recognizeBarcodeUseCase: RecognizeBarcodeUseCase,
    private val getProductFromBarcodeUseCase: GetProductFromBarcodeUseCase
) : ViewModel() {
    var listName = ""
    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList> = _shoppingList
    private val _recognizedProduct = MutableLiveData<Product>()
    val recognizedProduct: LiveData<Product> = _recognizedProduct
    private val _barcodeNotFound = MutableLiveData<String>()
    val barcodeNotFound: LiveData<String> = _barcodeNotFound
    var recognizerRunning = false

    fun loadShoppingList() {
        viewModelScope.launch {
            _shoppingList.value = getListsUseCase.getShoppingList(listName)
        }
    }

    fun insertProduct(name: String) {
        insertProduct(Product(name))
    }

    private fun insertProduct(product: Product) {
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

    fun recognizeBarcode(bitmap: Bitmap, rotation: Int) {
        if (recognizerRunning) return
        recognizerRunning = true
        viewModelScope.launch {
            val barcode = recognizeBarcodeUseCase.recognize(bitmap, rotation)
            if (barcode.isNotEmpty()) {
                try {
                    _recognizedProduct.value = getProductFromBarcodeUseCase.getProduct(barcode)
                } catch (ex: BarcodeNotFoundException) {
                    _barcodeNotFound.value = "Cannot recognize this product."
                }
            } else {
                recognizerRunning = false
            }
        }
    }
}