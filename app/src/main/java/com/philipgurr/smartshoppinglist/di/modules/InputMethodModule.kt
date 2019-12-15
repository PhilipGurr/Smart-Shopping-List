package com.philipgurr.smartshoppinglist.di.modules

import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.philipgurr.smartshoppinglist.productinput.ProductInputMethod
import com.philipgurr.smartshoppinglist.productinput.processors.BarcodeInputMethod
import dagger.Binds
import dagger.Module

@Module
interface InputMethodModule {
    @Binds
    fun bindBarcodeInputMethod(inputMethod: BarcodeInputMethod): ProductInputMethod<FirebaseVisionImage, String>
}