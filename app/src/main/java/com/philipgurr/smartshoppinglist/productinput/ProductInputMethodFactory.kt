package com.philipgurr.smartshoppinglist.productinput

import android.graphics.Bitmap
import com.philipgurr.smartshoppinglist.productinput.processors.BarcodeInputMethod
import com.philipgurr.smartshoppinglist.productinput.processors.TextInputMethod
import java.lang.IllegalArgumentException

object ProductInputMethodFactory {
    inline fun <reified T> create(): Nothing = when {
        //String is T -> TextInputMethod()
        //Bitmap is T ->  BarcodeInputMethod()
        else -> throw IllegalArgumentException("No ProductInputMethod found for the given type")
    }
}