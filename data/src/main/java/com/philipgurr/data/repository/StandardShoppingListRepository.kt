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

    override suspend fun deleteList(id: String) {
        shoppingListDatasource.delete(id)
    }

    override suspend fun getProducts(name: String) =
        shoppingListDatasource.getAllProducts(name.toId())

    override suspend fun addProduct(name: String, value: Product) {
        shoppingListDatasource.insertProduct(name.toId(), value)
    }

    override suspend fun deleteProduct(id: String, product: Product) {
        shoppingListDatasource.deleteProduct(id, product)
    }
}