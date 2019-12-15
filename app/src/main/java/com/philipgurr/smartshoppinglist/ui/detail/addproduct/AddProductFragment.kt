package com.philipgurr.smartshoppinglist.ui.detail.addproduct


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.vm.ListDetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_add_product.*
import org.jetbrains.anko.customView
import org.jetbrains.anko.editText
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import javax.inject.Inject

class AddProductFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProviders.of(activity!!, factory).get(ListDetailViewModel::class.java)
    }
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var choiceListAdapter: ProductChoiceListAdapter

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
        choiceListAdapter = ProductChoiceListAdapter { item ->
            when (item) {
                ProductInputMethod.TEXT_INPUT_METHOD -> addProductByText()
                ProductInputMethod.BARCODE_INPUT_METHOD -> addProductByBarcode()
            }
        }
        gridLayoutManager = GridLayoutManager(context, 2)
        addProductChoice.layoutManager = gridLayoutManager
        addProductChoice.adapter = choiceListAdapter
    }

    private fun addProductByText() {
        alert("New Product") {
            customView {
                val name = editText { hint = "Enter..." }
                okButton {
                    val text = name.text.toString()
                    viewModel.insertProduct(text)
                }
            }
        }.show()
    }

    private fun addProductByBarcode() {
        findNavController().navigate(R.id.camera_fragment)
    }
}
