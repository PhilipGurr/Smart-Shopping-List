package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.repository.Repository
import com.philipgurr.smartshoppinglist.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindRepository(repository: ShoppingListRepository): Repository
}