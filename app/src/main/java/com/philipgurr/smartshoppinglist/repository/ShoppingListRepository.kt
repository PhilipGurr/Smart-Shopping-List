package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.datasource.ShoppingListDatasource
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.util.extensions.toId
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val shoppingListDatasource: ShoppingListDatasource
) : Repository {

    override suspend fun getList(name: String): ShoppingList {
        require("" != name) { "Name must not be empty" }
        return shoppingListDatasource.get(name.toId())
    }

    override suspend fun getAllLists() = shoppingListDatasource.getAll()

    override suspend fun addList(value: ShoppingList) {
        shoppingListDatasource.insert(value)
    }

    override suspend fun addLists(values: List<ShoppingList>) {
        shoppingListDatasource.insertAll(values)
    }

    override suspend fun getProducts(shoppingListName: String) = shoppingListDatasource.getSubItems(shoppingListName.toId())

    override suspend fun addProduct(shoppingListName: String, value: Product) {
        shoppingListDatasource.insertSubItem(shoppingListName.toId(), value)
    }
}