package com.philipgurr.smartshoppinglist.productinput

import com.philipgurr.smartshoppinglist.productinput.processors.TextProductInputMethod
import java.lang.IllegalArgumentException

object ProductInputMethodFactory {
    fun create(type: String) = when(type) {
        ProductInputMethod.TEXT_INPUT_METHOD -> TextProductInputMethod()
        else -> throw IllegalArgumentException("No ProductInputMethod found for the given type")
    }
}