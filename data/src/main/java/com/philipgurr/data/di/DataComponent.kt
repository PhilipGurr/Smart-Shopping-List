package com.philipgurr.data.di

import com.philipgurr.data.di.modules.*
import com.philipgurr.domain.repository.RecognitionRepository
import com.philipgurr.domain.repository.ShoppingListRepository
import com.philipgurr.domain.repository.UpcRepository
import dagger.Component

@Component(
    modules = [
        ContextModule::class,
        ApiModule::class,
        DatasourceModule::class,
        FirebaseModule::class,
        RepositoryModule::class
    ]
)
interface DataComponent {
    fun getShoppingListRepository(): ShoppingListRepository
    fun getRecognitionRepository(): RecognitionRepository
    fun getUpcRepository(): UpcRepository
}