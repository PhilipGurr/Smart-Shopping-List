package com.philipgurr.smartshoppinglist.productinput.processors

import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod

class TextInputMethod : ProductInputMethod<String> {
    override fun process(value: String) = Product(value)
}