package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.data.api.BarcodeNotFoundException
import com.philipgurr.domain.Product
import com.philipgurr.domain.RecognitionImage
import com.philipgurr.domain.repository.RecognitionRepository
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.repository.UpcRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val recognitionRepository: RecognitionRepository,
    private val upcRepository: UpcRepository
) : ViewModel() {
    var listName = ""
    private val _recognizedProduct = MutableLiveData<Product>()
    val recognizedProduct: LiveData<Product> = _recognizedProduct
    private val _barcodeNotFound = MutableLiveData<String>()
    val barcodeNotFound: LiveData<String> = _barcodeNotFound
    var recognizerRunning = false

    fun insertProduct(name: String) {
        insertProduct(Product(name))
    }

    private fun insertProduct(product: Product) {
        viewModelScope.launch {
            shoppingListRepository.addProduct(listName, product)
        }
    }

    fun recognizeBarcode(image: RecognitionImage) {
        if (recognizerRunning) return
        recognizerRunning = true
        viewModelScope.launch {
            val barcode = recognitionRepository.recognize(image)
            if (barcode.isNotEmpty()) {
                try {
                    _recognizedProduct.value = upcRepository.getProduct(barcode)
                } catch (ex: BarcodeNotFoundException) {
                    _barcodeNotFound.value = "Cannot recognize this product."
                }
            } else {
                recognizerRunning = false
            }
        }
    }
}