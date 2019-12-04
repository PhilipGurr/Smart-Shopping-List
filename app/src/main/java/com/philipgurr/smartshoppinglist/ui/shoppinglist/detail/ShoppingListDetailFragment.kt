package com.philipgurr.smartshoppinglist.ui.shoppinglist.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.FragmentShoppingListDetailBinding
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.shoppinglist.ShoppingListToUIMapper
import com.philipgurr.smartshoppinglist.ui.shoppinglist.ShoppingListUI
import com.philipgurr.smartshoppinglist.vm.ShoppingListDetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_shopping_list_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import javax.inject.Inject

class ShoppingListDetailFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ShoppingListDetailViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val productListAdapter = ProductListAdapter()
    private val mapper = ShoppingListToUIMapper()
    private lateinit var shoppingList: ShoppingList
    private lateinit var shoppingListUi: ShoppingListUI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let { bundle ->
            val safeArgs =
                ShoppingListDetailFragmentArgs.fromBundle(bundle)
            shoppingList = safeArgs.shoppingList
            shoppingListUi = mapper.map(shoppingList)
        }

        val binding: FragmentShoppingListDetailBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_shopping_list_detail,
                container,
                false
            )
        binding.shoppingListUI = shoppingListUi
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.products.observe(this, Observer {
            setupRecyclerView(it)
        })
        setupFab()
    }

    private fun setupRecyclerView(products: List<Product>) {
        linearLayoutManager = LinearLayoutManager(context)
        productListRecyclerView.layoutManager = linearLayoutManager
        productListRecyclerView.adapter = productListAdapter

        productListAdapter.data = products
        productListAdapter.notifyDataSetChanged()
    }

    private fun setupFab() {
        fab_add_product.onClick {
            findNavController().navigate(R.id.nav_add_product_fragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts(shoppingList)
    }
}
