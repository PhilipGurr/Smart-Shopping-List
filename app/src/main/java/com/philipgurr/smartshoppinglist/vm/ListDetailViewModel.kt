package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.data.api.BarcodeNotFoundException
import com.philipgurr.domain.Product
import com.philipgurr.domain.RecognitionImage
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.barcode.Barcode
import com.philipgurr.domain.repository.RecognitionRepository
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.repository.UpcRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListDetailViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val recognitionRepository: RecognitionRepository,
    private val upcRepository: UpcRepository
) : ViewModel() {
    private lateinit var internalList: ShoppingList
    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList> = _shoppingList

    private var _recognizedBarcode = MutableLiveData<Barcode>()
    private var _recognizedProduct = MutableLiveData<Product>()
    private var _productNotFound = MutableLiveData<String>()
    private var recognizerRunning = false

    fun setShoppingList(list: ShoppingList) {
        internalList = list
        _shoppingList.value = internalList
    }

    fun getRecognizedBarcode(): LiveData<Barcode> = _recognizedBarcode

    fun getRecognizedProduct(): LiveData<Product> = _recognizedProduct

    fun getProductNotFound(): LiveData<String> = _productNotFound

    fun fetchNewestShoppingList() {
        viewModelScope.launch {
            internalList = shoppingListRepository.getList(internalList.name)
            refreshShoppingList()
        }
    }

    fun toggleCompleted(product: Product) {
        viewModelScope.launch {
            internalList.products.remove(product)
            val newProduct = Product(name = product.name, completed = !product.completed)
            insertProduct(newProduct) // TODO: Create toggle method in repository for setting product as completed instead of using insert
        }
    }

    fun insertCurrentProduct() {
        _recognizedProduct.value?.let {
            insertProduct(it)
        }
    }

    fun insertProduct(name: String) {
        insertProduct(
            Product(name)
        )
    }

    private fun insertProduct(product: Product) {
        viewModelScope.launch {
            internalList.products.add(product)
            shoppingListRepository.addProduct(internalList, product)
            refreshShoppingList()
        }
    }

    private fun refreshShoppingList() {
        _shoppingList.value = internalList
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            internalList.products.remove(product)
            shoppingListRepository.deleteProduct(internalList, product)
            refreshShoppingList()
        }
    }

    fun resetBarcodeRecognition() {
        _recognizedProduct = MutableLiveData()
        _productNotFound = MutableLiveData()
    }

    fun recognizeBarcode(image: RecognitionImage) {
        if (recognizerRunning) return
        recognizerRunning = true
        viewModelScope.launch {
            val barcode = recognitionRepository.recognize(image)
            if (barcode != null) {
                _recognizedBarcode.value = barcode
                try {
                    _recognizedProduct.value = upcRepository.getProduct(barcode.rawValue)
                } catch (ex: BarcodeNotFoundException) {
                    _productNotFound.value = "Cannot recognize this product."
                } finally {
                    recognizerRunning = false
                }
            } else {
                recognizerRunning = false
            }
        }
    }
}