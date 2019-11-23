package com.philipgurr.smartshoppinglist.ui.shoppinglist.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.vm.ShoppingListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_my_lists.*
import javax.inject.Inject

class ShoppingListFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val shoppingListAdapter =
        ShoppingListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.shoppingLists.observe(this, Observer { shoppingLists ->
            shoppingListAdapter.data = shoppingLists
            shoppingListAdapter.notifyDataSetChanged()
        })
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        myListsRecyclerView.layoutManager = linearLayoutManager
        myListsRecyclerView.adapter = shoppingListAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadShoppingLists()
    }
}