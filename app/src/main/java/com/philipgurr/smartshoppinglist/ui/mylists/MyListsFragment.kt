package com.philipgurr.smartshoppinglist.ui.mylists

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.ui.util.SwipeToDeleteCallback
import com.philipgurr.smartshoppinglist.ui.util.ToggleFabOnScroll
import com.philipgurr.smartshoppinglist.ui.util.closeKeyboard
import com.philipgurr.smartshoppinglist.ui.util.showKeyboard
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
    private val shoppingListAdapter =
        ShoppingListsAdapter()
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
        setHasOptionsMenu(true)
        setupRecyclerView()

        viewModel.shoppingLists.observe(this, Observer { shoppingLists ->
            shoppingListAdapter.data = shoppingLists
            shoppingListAdapter.notifyDataSetChanged()

            swipeRefreshMyLists.isRefreshing = false
        })

        swipeRefreshMyLists.setOnRefreshListener(this)

        // To prevent updating the lists while the user is deleting an item
        swipeRefreshMyLists.setOnChildScrollUpCallback { _, _ ->
            swipeToDeleteCallback.isSwiping
        }

        fab_add_list.onClick {
            createNewShoppingList()
        }

        viewModel.loadShoppingLists()
    }

    private fun setupRecyclerView() {
        swipeToDeleteCallback = SwipeToDeleteCallback(context!!) { viewHolder ->
            val position = viewHolder.adapterPosition
            deleteList(position)
        }
        linearLayoutManager = LinearLayoutManager(context)
        with(myListsRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = shoppingListAdapter

            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(this)
            addOnScrollListener(ToggleFabOnScroll(fab_add_list))
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
        alert(getString(R.string.dialog_new_list)) {
            customView {
                val name = editText { hint = context.getString(R.string.dialog_new_list_hint) }
                name.requestFocus()
                context!!.showKeyboard()
                okButton {
                    context!!.closeKeyboard()
                    val text = name.text.toString()
                    viewModel.createShoppingList(text)
                }
                onCancelled { context!!.closeKeyboard() }
            }
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.action_search)

        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null) return true
                viewModel.searchShoppingLists(newText)
                return true
            }
        })
    }
}