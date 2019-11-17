package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.datasource.Datasource
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val datasource: Datasource<ShoppingList>
) : Repository<ShoppingList> {

    override suspend fun get(name: String) = datasource.get(name)

    override suspend fun getAll() = datasource.getAll()

    override suspend fun add(value: ShoppingList) {
        datasource.insert(value)
    }

    override suspend fun addAll(values: List<ShoppingList>) {
        datasource.insertAll(values)
    }

}