package com.philipgurr.smartshoppinglist.ui.mylists

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.ui.ShoppingListsAdapter
import com.philipgurr.smartshoppinglist.ui.SwipeToDeleteCallback
import com.philipgurr.smartshoppinglist.vm.MyListsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_my_lists.*
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.okButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import javax.inject.Inject

class MyListsFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(MyListsViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val shoppingListAdapter = ShoppingListsAdapter()
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback

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

            swipeRefreshMyLists.isRefreshing = false
        })

        swipeRefreshMyLists.setOnRefreshListener(this)

        // Load data only if nothing is loaded so far to avoid unnecessary API calls
        // while navigating
        //if (viewModel.shoppingLists.value == null) {
        //    swipeRefreshMyLists.post { onRefresh() }
        //}

        fab_add_list.onClick {
            createNewShoppingList()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadShoppingLists()
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        with(myListsRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = shoppingListAdapter

            swipeToDeleteCallback = SwipeToDeleteCallback(context!!) { viewHolder ->
                val position = viewHolder.adapterPosition
                deleteList(position)
            }
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
        runWithPermissions(Manifest.permission.INTERNET) {
            swipeRefreshMyLists.isRefreshing = true
            viewModel.loadShoppingLists()
        }
    }

    private fun createNewShoppingList() {
        alert("New List") {
            customView {
                val name = editText { hint = "Enter name..." }
                okButton {
                    val text = name.text.toString()
                    viewModel.createShoppingList(text)
                }
            }
        }.show()
    }
}