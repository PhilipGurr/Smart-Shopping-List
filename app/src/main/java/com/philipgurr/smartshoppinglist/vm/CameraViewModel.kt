package com.philipgurr.smartshoppinglist.vm

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.smartshoppinglist.domain.usecases.RecognizeBarcodeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val recognizeBarcodeUseCase: RecognizeBarcodeUseCase
) : ViewModel() {
    private val _recognizedBarcodeData = MutableLiveData<String>()
    val recognizedBarcodeData: LiveData<String> = _recognizedBarcodeData

    fun recognizeBarcode(bitmap: Bitmap, rotation: Int) {
        viewModelScope.launch {
            val barcodeText = recognizeBarcodeUseCase.recognize(bitmap, rotation)
            if (barcodeText.isNotEmpty()) {
                _recognizedBarcodeData.value = barcodeText
            }
        }
    }
}