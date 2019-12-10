package com.philipgurr.smartshoppinglist.ui.completedlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.vm.CompletedListsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CompletedListsFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(CompletedListsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_completed_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        viewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}