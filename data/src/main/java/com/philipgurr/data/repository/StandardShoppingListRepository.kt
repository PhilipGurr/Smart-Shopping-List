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

    override suspend fun getNotCompletedLists() = getAllLists().filter { shoppingList ->
        shoppingList.products.size == 0 || shoppingList.completedProducts().size < shoppingList.products.size
    }

    override suspend fun getCompletedLists() = getAllLists().filter { shoppingList ->
        shoppingList.completedProducts().size == shoppingList.products.size
    }

    override suspend fun addList(value: ShoppingList) {
        shoppingListDatasource.insert(value)
    }

    override suspend fun addLists(values: List<ShoppingList>) {
        shoppingListDatasource.insertAll(values)
    }

    override suspend fun deleteList(id: String) {
        shoppingListDatasource.delete(id)
    }

    override suspend fun getProducts(list: ShoppingList) =
        shoppingListDatasource.getAllProducts(list.id)

    override suspend fun addProduct(list: ShoppingList, value: Product) {
        shoppingListDatasource.insertProduct(list.id, value)
    }

    override suspend fun deleteProduct(list: ShoppingList, product: Product) {
        shoppingListDatasource.deleteProduct(list.id, product)
    }
}