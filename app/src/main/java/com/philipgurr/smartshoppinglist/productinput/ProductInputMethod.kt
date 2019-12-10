package com.philipgurr.smartshoppinglist.productinput

import com.philipgurr.smartshoppinglist.domain.Product

interface ProductInputMethod<T> {
    fun process(value: T): Product

    companion object {
        const val TEXT_INPUT_METHOD = "Text"
        const val BARCODE_INPUT_METHOD = "Barcode"
    }
}