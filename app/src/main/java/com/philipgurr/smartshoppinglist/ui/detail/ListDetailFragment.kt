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
import com.philipgurr.smartshoppinglist.ui.util.SwipeToDeleteCallback
import com.philipgurr.smartshoppinglist.ui.util.closeKeyboard
import com.philipgurr.smartshoppinglist.ui.util.showKeyboard
import com.philipgurr.smartshoppinglist.util.extensions.initFab
import com.philipgurr.smartshoppinglist.util.extensions.rotateFab
import com.philipgurr.smartshoppinglist.util.extensions.showFabIn
import com.philipgurr.smartshoppinglist.util.extensions.showFabOut
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_shopping_list_detail.*
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import javax.inject.Inject

class ListDetailFragment : DaggerFragment(), SwipeRefreshLayout.OnRefreshListener {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ListDetailViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var binding: FragmentShoppingListDetailBinding
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback
    private var rotateFab = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parseArguments()
        productListAdapter = ProductListAdapter(viewModel)
        binding = setupDataBinding(inflater, container)
        return binding.root
    }

    private fun parseArguments() {
        arguments?.let { bundle ->
            val safeArgs = ListDetailFragmentArgs.fromBundle(bundle)
            viewModel.setShoppingList(safeArgs.shoppingList)
        }
    }

    private fun setupDataBinding(
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
        setupFabs()

        viewModel.shoppingList.observe(this, Observer { list ->
            productListAdapter.data = list.getSortedProducts()
            productListAdapter.notifyDataSetChanged()

            swipeRefreshDetail.isRefreshing = false
        })

        swipeRefreshDetail.setOnRefreshListener(this)
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        with(productListRecyclerView) {
            layoutManager = linearLayoutManager
            adapter = productListAdapter

            swipeToDeleteCallback =
                SwipeToDeleteCallback(
                    context!!
                ) { viewHolder ->
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

    private fun setupFabs() {
        fab_barcode_input.initFab()
        fab_text_input.initFab()

        fab_add_product.onClick {
            animateAddProductFabs()
        }

        fab_text_input.onClick {
            addProductByText()
            animateAddProductFabs()
        }
        fab_barcode_input.onClick {
            addProductByBarcode()
            animateAddProductFabs()
        }
    }

    private fun animateAddProductFabs() {
        rotateFab = !rotateFab
        fab_add_product.rotateFab(rotateFab)
        if (rotateFab) {
            fab_text_input.showFabIn()
            fab_barcode_input.showFabIn()
        } else {
            fab_text_input.showFabOut()
            fab_barcode_input.showFabOut()
        }
    }

    private fun addProductByText() {
        alert(getString(R.string.dialog_new_product)) {
            customView {
                val name = editText { hint = context.getString(R.string.dialog_new_product_hint) }
                name.requestFocus()
                context!!.showKeyboard()
                positiveButton(getString(R.string.dialog_new_product_positive_button)) {
                    context!!.closeKeyboard()
                    val text = name.text.toString()
                    viewModel.insertProduct(text)
                    addProductByText()
                }
                onCancelled { context!!.closeKeyboard() }
            }
        }.show()
    }

    private fun addProductByBarcode() {
        val action = ListDetailFragmentDirections.actionNavShoppingListDetailToCameraFragment(
            viewModel.shoppingList.value!!
        )
        findNavController().navigate(action)
    }

    override fun onRefresh() {
        swipeRefreshDetail.isRefreshing = true
        viewModel.fetchNewestShoppingList()
    }
}
