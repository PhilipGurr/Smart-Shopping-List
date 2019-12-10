package com.philipgurr.smartshoppinglist.productinput.processors

import android.graphics.Bitmap
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod

class BarcodeInputMethod : ProductInputMethod<Bitmap> {
    override fun process(value: Bitmap): Product {
        return Product()
    }
}