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
import androidx.recyclerview.widget.LinearLayoutManager
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.databinding.FragmentShoppingListDetailBinding
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_shopping_list_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import javax.inject.Inject

class ListDetailFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ListDetailViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var binding: FragmentShoppingListDetailBinding

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
            viewModel.listName = safeArgs.shoppingList.name
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
        })
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        productListRecyclerView.layoutManager = linearLayoutManager
        productListRecyclerView.adapter = productListAdapter
    }

    private fun setupFab() {
        fab_add_product.onClick {
            findNavController().navigate(R.id.nav_add_product_fragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadShoppingList()
    }
}
