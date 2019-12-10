package com.philipgurr.smartshoppinglist.productinput

object ProductInputMethodFactory {
    inline fun <reified T> create(): Nothing = when {
        //String is T -> TextInputMethod()
        //Bitmap is T ->  BarcodeInputMethod()
        else -> throw IllegalArgumentException("No ProductInputMethod found for the given type")
    }
}