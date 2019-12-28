package com.philipgurr.smartshoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.usecases.AddListUseCase
import com.philipgurr.domain.usecases.GetListsUseCase
import com.philipgurr.domain.util.toId
import com.philipgurr.smartshoppinglist.vm.MyListsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

private const val SHOPPING_LIST_NAME = "Test Shopping List"

class MyListsViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val repository: ShoppingListRepository = mock()
    private val shoppingListsUseCase = GetListsUseCase(repository)
    private val addListsUseCase = AddListUseCase(repository)
    private val viewModel = MyListsViewModel(shoppingListsUseCase, addListsUseCase)

    private val testProduct = Product("Banana", false)
    private val testProduct2 =
        Product("Strawberry Jam", true)
    private val testShoppingList = ShoppingList(
        SHOPPING_LIST_NAME.toId(),
        SHOPPING_LIST_NAME,
        Date(),
        listOf(testProduct, testProduct2)
    )

    @Before
    fun before() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun testGetLists() = runBlocking(Dispatchers.Main) {
        whenever(repository.getAllLists()).thenReturn(listOf(testShoppingList))

        viewModel.loadShoppingLists()
        assertEquals(listOf(testShoppingList), viewModel.shoppingLists.blockingObserve())
    }

    fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}