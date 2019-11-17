package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.datasource.DataSource
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val dataSource: DataSource<ShoppingList>
) : Repository<ShoppingList> {

    override suspend fun get(name: String) = dataSource.get(name)

    override suspend fun getAll() = dataSource.getAll()

    override suspend fun add(value: ShoppingList) {
        dataSource.insert(value)
    }

    override suspend fun addAll(values: List<ShoppingList>) {
        dataSource.insertAll(values)
    }

}