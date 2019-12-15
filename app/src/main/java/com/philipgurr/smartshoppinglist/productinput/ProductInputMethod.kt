package com.philipgurr.smartshoppinglist.productinput

interface ProductInputMethod<T, R> {
    suspend fun process(value: T): R

    companion object {
        const val TEXT_INPUT_METHOD = "Text"
        const val BARCODE_INPUT_METHOD = "Barcode"
    }
}