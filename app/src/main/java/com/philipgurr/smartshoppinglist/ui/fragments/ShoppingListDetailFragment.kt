package com.philipgurr.smartshoppinglist.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.ui.ProductListAdapter
import com.philipgurr.smartshoppinglist.ui.ShoppingListToUIMapper
import com.philipgurr.smartshoppinglist.ui.ShoppingListUI
import kotlinx.android.synthetic.main.fragment_shopping_list_detail.*
import kotlinx.android.synthetic.main.recyclerview_item.*

/**
 * A simple [Fragment] subclass.
 */
class ShoppingListDetailFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val productListAdapter = ProductListAdapter()
    private val mapper = ShoppingListToUIMapper()
    private lateinit var shoppingList: ShoppingList
    private lateinit var shoppingListUi: ShoppingListUI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            val safeArgs = ShoppingListDetailFragmentArgs.fromBundle(it)
            shoppingList = safeArgs.shoppingList
            shoppingListUi = mapper.map(shoppingList)
        }

        return inflater.inflate(R.layout.fragment_shopping_list_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeader()
        setupRecyclerView()
    }

    private fun setupHeader() = with(shoppingListUi) {
            shoppingListNameDetail.text = name
            shoppingListProgressDetail.text = "${progress}/${totalProducts}"

            shoppingListProgressBarDetail.max = totalProducts
            shoppingListProgressBarDetail.progress = progress
            // TODO: "Set progressbar color"
        }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context)
        productListRecyclerView.layoutManager = linearLayoutManager
        productListRecyclerView.adapter = productListAdapter

        productListAdapter.data = shoppingList.products
        productListAdapter.notifyDataSetChanged()
    }
}
