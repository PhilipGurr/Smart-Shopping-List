package com.philipgurr.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.data.database.ShoppingListDatasource
import com.philipgurr.data.repository.StandardShoppingListRepository
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.util.toId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

private const val SHOPPING_LIST_NAME = "Test Shopping List"

class RepositoryTest {
    private val shoppingListDatasource: ShoppingListDatasource = mock()
    private val repository = StandardShoppingListRepository(shoppingListDatasource)

    private val now = Date()
    private val testProduct = Product("Banana", now, false)
    private val testProduct2 =
        Product("Strawberry Jam", now, false)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        SHOPPING_LIST_NAME,
        Date(),
        mutableListOf(testProduct, testProduct2)
    )

    @Test
    fun testShoppingListRepositoryGetAll() = runBlocking {
        whenever(shoppingListDatasource.getAll()).thenReturn(listOf(testShoppingList))

        val actualShoppingLists = repository.getAllLists()
        assertEquals(listOf(testShoppingList), actualShoppingLists)
    }

    @Test
    fun testShoppingListRepositoryGetSingle() = runBlocking {
        whenever(shoppingListDatasource.get(SHOPPING_LIST_NAME.toId())).thenReturn(testShoppingList)

        val actualShoppingList = repository.getList(SHOPPING_LIST_NAME)
        assertEquals(testShoppingList, actualShoppingList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testShoppingListRepositoryWithEmptyName() {
        runBlocking {
            repository.getList("")
        }
    }
}