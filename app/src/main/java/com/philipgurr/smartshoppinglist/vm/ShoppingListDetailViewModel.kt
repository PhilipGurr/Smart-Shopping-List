package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.ViewModel
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import javax.inject.Inject

class ShoppingListDetailViewModel @Inject constructor(

) : ViewModel() {
    lateinit var shoppingList: ShoppingList

    fun getProducts() = shoppingList.products
}