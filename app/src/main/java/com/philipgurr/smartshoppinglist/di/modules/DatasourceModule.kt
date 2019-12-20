package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.data.api.BatzoDatasource
import com.philipgurr.smartshoppinglist.data.api.UpcDatasource
import com.philipgurr.smartshoppinglist.data.database.FirebaseCloudShoppingListDatasource
import com.philipgurr.smartshoppinglist.data.database.ShoppingListDatasource
import dagger.Binds
import dagger.Module

@Module
interface DatasourceModule {
    @Binds
    fun bindCloudDatasource(datasourceFirebase: FirebaseCloudShoppingListDatasource): ShoppingListDatasource

    @Binds
    fun bindUpcDatasource(batzoDatasource: BatzoDatasource): UpcDatasource
}