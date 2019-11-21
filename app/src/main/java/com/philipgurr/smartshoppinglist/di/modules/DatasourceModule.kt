package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.datasource.Datasource
import com.philipgurr.smartshoppinglist.datasource.FirebaseCloudDatasource
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import dagger.Binds
import dagger.Module

@Module
interface DatasourceModule {
    @Binds
    fun bindCloudDataSource(datasourceFirebase: FirebaseCloudDatasource): Datasource<ShoppingList>
}