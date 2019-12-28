package com.philipgurr.data.repository

import com.philipgurr.data.database.ShoppingListDatasource
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.util.toId
import javax.inject.Inject

class StandardShoppingListRepository @Inject constructor(
    private val shoppingListDatasource: ShoppingListDatasource
) : ShoppingListRepository {

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

    override suspend fun getProducts(shoppingListName: String) =
        shoppingListDatasource.getAllProduct(shoppingListName.toId())

    override suspend fun addProduct(shoppingListName: String, value: Product) {
        shoppingListDatasource.insertProduct(shoppingListName.toId(), value)
    }
}