package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.datasource.ShoppingListDatasource
import com.philipgurr.smartshoppinglist.datasource.FirebaseCloudShoppingListDatasource
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import dagger.Binds
import dagger.Module

@Module
interface DatasourceModule {
    @Binds
    fun bindCloudDataSource(datasourceFirebase: FirebaseCloudShoppingListDatasource): ShoppingListDatasource
}