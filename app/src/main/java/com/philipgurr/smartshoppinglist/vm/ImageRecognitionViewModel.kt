package com.philipgurr.smartshoppinglist.vm

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.domain.usecases.RecognizeBarcodeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageRecognitionViewModel @Inject constructor(
    private val recognizeBarcodeUseCase: RecognizeBarcodeUseCase
) : ViewModel() {
    private val _recognizedBarcodeData = MutableLiveData<String>()
    val recognizedBarcodeData: LiveData<String> = _recognizedBarcodeData

    fun recognizeBarcode(image: Bitmap) {
        viewModelScope.launch {
            _recognizedBarcodeData.value = recognizeBarcodeUseCase.recognize(image)
        }
    }
}