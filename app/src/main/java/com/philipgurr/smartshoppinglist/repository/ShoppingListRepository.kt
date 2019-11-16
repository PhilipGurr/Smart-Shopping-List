package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import javax.inject.Inject

class ShoppingListRepository @Inject constructor() : Repository<ShoppingList> {
    override fun get(): ShoppingList {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<ShoppingList> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(value: ShoppingList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(values: List<ShoppingList>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}