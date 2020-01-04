package com.philipgurr.smartshoppinglist.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.usecases.DeleteListUseCase
import com.philipgurr.domain.usecases.GetCompletedListsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompletedListsViewModel @Inject constructor(
    private val getCompletedListsUseCase: GetCompletedListsUseCase,
    private val deleteListUseCase: DeleteListUseCase
) : ViewModel() {
    private val _completedLists = MutableLiveData<List<ShoppingList>>()
    val completedLists: LiveData<List<ShoppingList>> = _completedLists

    fun loadShoppingLists() {
        viewModelScope.launch {
            _completedLists.value = getCompletedListsUseCase.getCompletedLists()
        }
    }

    fun deleteShoppingList(name: String) {
        viewModelScope.launch {
            deleteListUseCase.delete(name)
        }
    }
}