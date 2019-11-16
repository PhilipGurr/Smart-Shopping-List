package com.philipgurr.smartshoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.domain.usecases.GetShoppingListsUseCase
import com.philipgurr.smartshoppinglist.repository.Repository
import com.philipgurr.smartshoppinglist.vm.ShoppingListViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingListViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val repository: Repository<ShoppingList> = mock()
    private val shoppingListsUseCase = GetShoppingListsUseCase(repository)

    private val testProduct = Product("Banana", false)
    private val testProduct2 = Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        "testShoppingList",
        listOf(testProduct, testProduct2)
    )

    @Before
    fun before() {
        whenever(repository.getAll()).thenReturn(listOf(testShoppingList))
    }

    @Test
    fun testShoppingViewModel() {
        val viewModel = ShoppingListViewModel(shoppingListsUseCase)
        viewModel.loadShoppingLists()
        assertEquals(listOf(testShoppingList), viewModel.shoppingList.value)
    }
}