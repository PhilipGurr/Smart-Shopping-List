package com.philipgurr.smartshoppinglist.ui.addproduct


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.philipgurr.smartshoppinglist.R
import kotlinx.android.synthetic.main.fragment_add_product.*

class AddProductFragment : Fragment() {
    private lateinit var gridLayoutManager: GridLayoutManager
    private val choiceAdapter = ProductChoiceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        gridLayoutManager = GridLayoutManager(context, 2)
        addProductChoice.layoutManager = gridLayoutManager
        addProductChoice.adapter = choiceAdapter
    }
}
