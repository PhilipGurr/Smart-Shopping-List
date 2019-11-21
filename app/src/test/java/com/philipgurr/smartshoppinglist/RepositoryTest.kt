package com.philipgurr.smartshoppinglist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.smartshoppinglist.datasource.Datasource
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {
    private val datasource: Datasource<ShoppingList> = mock()
    private val repository = ShoppingListRepository(datasource)

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

        val actualShoppingLists = repository.getAll()
        assertEquals(listOf(testShoppingList), actualShoppingLists)
    }

    @Test
    fun testShoppingListRepositoryGetSingle() = runBlocking {
        whenever(datasource.get("Test Shopping List")).thenReturn(testShoppingList)

        val actualShoppingList = repository.get("Test Shopping List")
        assertEquals(testShoppingList, actualShoppingList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testShoppingListRepositoryWithEmptyName() {
        runBlocking {
            repository.get("")
        }
    }
}