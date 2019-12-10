package com.philipgurr.smartshoppinglist.ui.addproduct


import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.philipgurr.smartshoppinglist.R
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.productinput.processors.BarcodeInputMethod
import com.philipgurr.smartshoppinglist.productinput.processors.TextInputMethod
import com.philipgurr.smartshoppinglist.vm.ShoppingListDetailViewModel
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
        ViewModelProviders.of(activity!!, factory).get(ShoppingListDetailViewModel::class.java)
    }
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var choiceAdapter: ProductChoiceAdapter

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
        choiceAdapter = ProductChoiceAdapter { item ->
            val textInputMethod = TextInputMethod()
            val barcodeInputMethod = BarcodeInputMethod()
            when(item) {
                ProductInputMethod.TEXT_INPUT_METHOD -> addProductByText(textInputMethod)
                ProductInputMethod.BARCODE_INPUT_METHOD -> addProductByBarcode(barcodeInputMethod)
            }
        }
        gridLayoutManager = GridLayoutManager(context, 2)
        addProductChoice.layoutManager = gridLayoutManager
        addProductChoice.adapter = choiceAdapter
    }

    private fun addProductByText(inputMethod: ProductInputMethod<String>) {
        alert("New Product") {
            customView {
                val name = editText { hint = "Enter..." }
                okButton {
                    val text = name.text.toString()
                    val newProduct = inputMethod.process(text)
                    viewModel.insertProduct(newProduct)
                }
            }
        }.show()
    }

    private fun addProductByBarcode(inputMethod: ProductInputMethod<Bitmap>) {
        findNavController().navigate(R.id.camera_fragment)
    }
}
