package com.philipgurr.data.di.modules

import com.philipgurr.data.repository.BarcodeRecognitionRepository
import com.philipgurr.data.repository.StandardShoppingListRepository
import com.philipgurr.data.repository.StandardUpcRepository
import com.philipgurr.domain.repository.RecognitionRepository
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.repository.UpcRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindShoppingListRepository(repository: StandardShoppingListRepository): ShoppingListRepository

    @Binds
    fun bindUpcRepository(repository: StandardUpcRepository): UpcRepository

    @Binds
    fun bindBarcodeRecognitionRepository(repository: BarcodeRecognitionRepository): RecognitionRepository
}