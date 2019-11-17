package com.philipgurr.smartshoppinglist

import com.philipgurr.smartshoppinglist.datasource.FirebaseCloudDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class DatasourceTest {

    @Before
    fun before() {
       Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun testCloudDataSourceGetSingle() = runBlocking(Dispatchers.Main) {
        val cloudDatasource = FirebaseCloudDatasource()

        val shoppingList = cloudDatasource.get("testShoppingList")

    }
}