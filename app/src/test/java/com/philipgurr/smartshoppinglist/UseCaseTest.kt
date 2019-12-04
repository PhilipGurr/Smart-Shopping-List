package com.philipgurr.smartshoppinglist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.GetListsUseCase
import com.philipgurr.smartshoppinglist.repository.Repository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*

class UseCaseTest {
    private val repository: Repository = mock()

    private val testProduct = Product("Banana", false)
    private val testProduct2 = Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        "Test Shopping List",
        Date(),
        listOf(testProduct, testProduct2)
    )

    @Test
    fun testShoppingListUseCase() = runBlocking {
        whenever(repository.getAllLists()).thenReturn(listOf(testShoppingList))

        val getshoppingListUseCase = GetListsUseCase(repository)
        val actualShoppingLists = getshoppingListUseCase.getShoppingLists()

        assertEquals(listOf(testShoppingList), actualShoppingLists)
    }
}