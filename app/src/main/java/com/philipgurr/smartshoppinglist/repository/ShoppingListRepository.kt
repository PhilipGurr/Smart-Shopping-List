package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList

interface ShoppingListRepository {
    suspend fun getList(name: String): ShoppingList
    suspend fun getAllLists(): List<ShoppingList>
    suspend fun addList(value: ShoppingList)
    suspend fun addLists(values: List<ShoppingList>)
    suspend fun getProducts(shoppingListName: String): List<Product>
    suspend fun addProduct(shoppingListName: String, value: Product)
}