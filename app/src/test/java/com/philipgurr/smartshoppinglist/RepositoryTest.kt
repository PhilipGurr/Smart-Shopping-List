package com.philipgurr.smartshoppinglist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.smartshoppinglist.datasource.Datasource
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryTest {
    private val datasource: Datasource<ShoppingList> = mock()

    private val testProduct = Product("Banana", false)
    private val testProduct2 = Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        "Test Shopping List",
        listOf(testProduct, testProduct2)
    )

    @Test
    fun testShoppingListRepositoryGetAll() = runBlocking {
        whenever(datasource.getAll()).thenReturn(listOf(testShoppingList))

        val repository = ShoppingListRepository(datasource)
        val actualShoppingLists = repository.getAll()

        assertEquals(listOf(testShoppingList), actualShoppingLists)
    }

    @Test
    fun testShoppingListRepositoryGetSingle() = runBlocking {
        whenever(datasource.get("Test Shopping List")).thenReturn(testShoppingList)

        val repository = ShoppingListRepository(datasource)
        val actualShoppingList = repository.get("Test Shopping List")

        assertEquals(testShoppingList, actualShoppingList)
    }
}