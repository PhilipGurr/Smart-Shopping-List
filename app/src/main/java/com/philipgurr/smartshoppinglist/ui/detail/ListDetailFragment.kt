package com.philipgurr.smartshoppinglist.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.FragmentShoppingListDetailBinding
import com.philipgurr.smartshoppinglist.ui.SwipeToDeleteCallback
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_shopping_list_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import javax.inject.Inject

class ListDetailFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(ListDetailViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var binding: FragmentShoppingListDetailBinding
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parseArguments()
        productListAdapter = ProductListAdapter(viewModel)
        binding = setUpDataBinding(inflater, container)
        return binding.root
    }

    private fun parseArguments() {
        arguments?.let { bundle ->
            val safeArgs = ListDetailFragmentArgs.fromBundle(bundle)
            val listName = safeArgs.shoppingList.name
            viewModel.listName = listName
        }
    }

    private fun setUpDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShoppingListDetailBinding {
        val binding: FragmentShoppingListDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shopping_list_detail,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()

        viewModel.shoppingList.observe(this, Observer { list ->
            productListAdapter.data = list.products
            productListAdapter.notifyDataSetChanged()

            swipeRefreshDetail.isRefreshing = false
        })

        swipeRefreshDetail.setOnRefreshListener(this)
        swipeRefreshDetail.post { onRefresh() }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        with(productListRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = productListAdapter

            swipeToDeleteCallback = SwipeToDeleteCallback(context!!) { viewHolder ->
                val position = viewHolder.adapterPosition
                deleteProduct(position)
            }
            ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(this)
        }
    }

    private fun deleteProduct(position: Int) {
        val productToDelete = productListAdapter.data[position]
        productListAdapter.removeItem(position)
        productListAdapter.notifyItemRemoved(position)
        viewModel.deleteProduct(productToDelete)
    }

    private fun setupFab() {
        fab_add_product.onClick {
            val actionAdd =
                ListDetailFragmentDirections.actionNavShoppingListDetailToAddProductFragment(
                    viewModel.shoppingList.value!!
                )
            findNavController().navigate(actionAdd)
        }
    }

    private fun shouldReloadData(listName: String) =
        viewModel.shoppingList.value == null || viewModel.listName != listName

    override fun onRefresh() {
        swipeRefreshDetail.isRefreshing = true
        viewModel.loadShoppingList()
    }
}
