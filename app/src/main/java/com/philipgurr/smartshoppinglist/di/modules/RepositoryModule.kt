package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import com.philipgurr.smartshoppinglist.repository.StandardShoppingListRepository
import com.philipgurr.smartshoppinglist.repository.StandardUpcRepository
import com.philipgurr.smartshoppinglist.repository.UpcRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindShoppingListRepository(repository: StandardShoppingListRepository): ShoppingListRepository

    @Binds
    fun bindUpcRepository(repository: StandardUpcRepository): UpcRepository
}