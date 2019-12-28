package com.philipgurr.data.di.modules

import com.philipgurr.data.api.BatzoDatasource
import com.philipgurr.data.api.UpcDatasource
import com.philipgurr.data.database.FirebaseCloudShoppingListDatasource
import com.philipgurr.data.database.ShoppingListDatasource
import dagger.Binds
import dagger.Module

@Module
interface DatasourceModule {
    @Binds
    fun bindCloudDatasource(datasourceFirebase: FirebaseCloudShoppingListDatasource): ShoppingListDatasource

    @Binds
    fun bindUpcDatasource(batzoDatasource: BatzoDatasource): UpcDatasource
}