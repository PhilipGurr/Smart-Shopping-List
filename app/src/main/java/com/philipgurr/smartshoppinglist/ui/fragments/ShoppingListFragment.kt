package com.philipgurr.smartshoppinglist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.vm.ShoppingListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShoppingListFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_lists, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.shoppingList.observe(this, Observer {
            textView.text = "Sample text"
        })

        return root
    }
}