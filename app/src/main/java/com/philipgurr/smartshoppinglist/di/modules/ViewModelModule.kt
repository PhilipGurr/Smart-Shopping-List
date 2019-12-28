package com.philipgurr.smartshoppinglist.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.philipgurr.smartshoppinglist.di.ViewModelKey
import com.philipgurr.smartshoppinglist.vm.*
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
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    abstract fun bindAddProductViewModel(viewModel: AddProductViewModel): ViewModel

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}