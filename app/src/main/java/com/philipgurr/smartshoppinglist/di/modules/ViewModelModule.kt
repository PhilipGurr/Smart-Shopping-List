package com.philipgurr.smartshoppinglist.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.philipgurr.smartshoppinglist.di.ViewModelKey
import com.philipgurr.smartshoppinglist.vm.CompletedListsViewModel
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
import com.philipgurr.smartshoppinglist.vm.MyListsViewModel
import com.philipgurr.smartshoppinglist.vm.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyListsViewModel::class)
    abstract fun bindShoppingListViewModel(viewModel: MyListsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompletedListsViewModel::class)
    abstract fun bindCompletedShoppingListViewModel(viewModel: CompletedListsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListDetailViewModel::class)
    abstract fun bindShoppingListDetailViewModel(viewModel: ListDetailViewModel): ViewModel

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}