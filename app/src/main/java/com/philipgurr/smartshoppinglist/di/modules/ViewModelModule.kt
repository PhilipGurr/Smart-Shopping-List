package com.philipgurr.smartshoppinglist.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.philipgurr.smartshoppinglist.di.ViewModelKey
import com.philipgurr.smartshoppinglist.util.ViewModelFactory
import com.philipgurr.smartshoppinglist.vm.CompletedShoppingListViewModel
import com.philipgurr.smartshoppinglist.vm.ShoppingListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListViewModel::class)
    abstract fun bindShoppingListViewModel(viewModel: ShoppingListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompletedShoppingListViewModel::class)
    abstract fun bindCompletedShoppingListViewModel(viewModel: CompletedShoppingListViewModel): ViewModel

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}