package com.philipgurr.smartshoppinglist.ui.completedlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.ui.ShoppingListsAdapter
import com.philipgurr.smartshoppinglist.vm.CompletedListsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_completed_list.*
import javax.inject.Inject

class CompletedListsFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(CompletedListsViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val shoppingListAdapter =
        ShoppingListsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_completed_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.completedLists.observe(this, Observer { shoppingLists ->
            shoppingListAdapter.data = shoppingLists
            shoppingListAdapter.notifyDataSetChanged()

            swipeRefreshCompletedLists.isRefreshing = false
        })

        swipeRefreshCompletedLists.setOnRefreshListener(this)
        swipeRefreshCompletedLists.post { onRefresh() }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        completedListsRecyclerView.layoutManager = linearLayoutManager
        completedListsRecyclerView.adapter = shoppingListAdapter
    }

    override fun onRefresh() {
        swipeRefreshCompletedLists.isRefreshing = true
        viewModel.loadShoppingLists()
    }
}