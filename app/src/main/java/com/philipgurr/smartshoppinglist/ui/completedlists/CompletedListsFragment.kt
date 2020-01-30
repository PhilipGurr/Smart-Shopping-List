package com.philipgurr.smartshoppinglist.ui.completedlists

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.ui.util.SwipeToDeleteCallback
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
        CompletedListsAdapter()
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_completed_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()

        viewModel.completedLists.observe(this, Observer { shoppingLists ->
            shoppingListAdapter.data = shoppingLists
            shoppingListAdapter.notifyDataSetChanged()

            swipeRefreshCompletedLists.isRefreshing = false
        })

        swipeRefreshCompletedLists.setOnRefreshListener(this)

        // To prevent updating the lists while the user is deleting an item
        swipeRefreshCompletedLists.setOnChildScrollUpCallback { _, _ ->
            swipeToDeleteCallback.isSwiping
        }
        viewModel.loadShoppingLists()
    }

    private fun setupRecyclerView() {
        swipeToDeleteCallback = SwipeToDeleteCallback(context!!) { viewHolder ->
            val position = viewHolder.adapterPosition
            deleteList(position)
        }
        linearLayoutManager = LinearLayoutManager(context)
        with(completedListsRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = shoppingListAdapter
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(this)
        }
    }

    private fun deleteList(position: Int) {
        val listToDelete = shoppingListAdapter.data[position]
        shoppingListAdapter.removeItem(position)
        shoppingListAdapter.notifyItemRemoved(position)
        viewModel.deleteShoppingList(listToDelete.name)
    }

    override fun onRefresh() {
        swipeRefreshCompletedLists.isRefreshing = true
        viewModel.loadShoppingLists()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.action_search)

        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(SearchTextListener())
    }

    private inner class SearchTextListener : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText == null) return true
            viewModel.searchShoppingLists(newText)
            return true
        }
    }
}