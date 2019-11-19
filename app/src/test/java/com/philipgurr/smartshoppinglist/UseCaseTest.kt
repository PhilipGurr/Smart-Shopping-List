package com.philipgurr.smartshoppinglist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.GetShoppingListsUseCase
import com.philipgurr.smartshoppinglist.repository.Repository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UseCaseTest {
    private val repository: Repository<ShoppingList> = mock()

    private val testProduct = Product("Banana", false)
    private val testProduct2 = Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        "Test Shopping List",
        listOf(testProduct, testProduct2)
    )

    @Test
    fun testShoppingListUseCase() = runBlocking {
        whenever(repository.getAll()).thenReturn(listOf(testShoppingList))

        val getshoppingListUseCase = GetShoppingListsUseCase(repository)
        val actualShoppingLists = getshoppingListUseCase.getShoppingLists()

        assertEquals(listOf(testShoppingList), actualShoppingLists)
    }
}