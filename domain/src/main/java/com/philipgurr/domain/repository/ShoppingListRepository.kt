package com.philipgurr.domain.repository

import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList

interface ShoppingListRepository {
    suspend fun getList(name: String): ShoppingList
    suspend fun getAllLists(): List<ShoppingList>
    suspend fun getNotCompletedLists(): List<ShoppingList>
    suspend fun getCompletedLists(): List<ShoppingList>
    suspend fun addList(value: ShoppingList)
    suspend fun addLists(values: List<ShoppingList>)
    suspend fun deleteList(id: String)
    suspend fun getProducts(list: ShoppingList): List<Product>
    suspend fun addProduct(list: ShoppingList, value: Product)
    suspend fun deleteProduct(list: ShoppingList, product: Product)
}