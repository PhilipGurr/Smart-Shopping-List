package com.philipgurr.data.database

import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList

interface ShoppingListDatasource {
    suspend fun get(name: String): ShoppingList
    suspend fun getAll(): List<ShoppingList>
    suspend fun insert(value: ShoppingList)
    suspend fun insertAll(values: List<ShoppingList>)
    suspend fun getAllProduct(shoppingListName: String): List<Product>
    suspend fun insertProduct(shoppingListId: String, value: Product)
}