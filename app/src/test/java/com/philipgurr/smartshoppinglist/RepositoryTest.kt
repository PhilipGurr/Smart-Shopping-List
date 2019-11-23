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
import java.util.*

private const val SHOPPING_LIST_NAME = "Test Shopping List"

class RepositoryTest {
    private val datasource: Datasource<ShoppingList> = mock()
    private val repository = ShoppingListRepository(datasource)

    private val testProduct = Product("Banana", false)
    private val testProduct2 = Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        SHOPPING_LIST_NAME,
        Date(),
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
        whenever(datasource.get(SHOPPING_LIST_NAME)).thenReturn(testShoppingList)

        val actualShoppingList = repository.get(SHOPPING_LIST_NAME)
        assertEquals(testShoppingList, actualShoppingList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testShoppingListRepositoryWithEmptyName() {
        runBlocking {
            repository.get("")
        }
    }
}