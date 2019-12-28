package com.philipgurr.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.usecases.GetListsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class UseCaseTest {
    private val repository: ShoppingListRepository = mock()

    private val testProduct = Product("Banana", false)
    private val testProduct2 =
        Product("Strawberry Jam", true)
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