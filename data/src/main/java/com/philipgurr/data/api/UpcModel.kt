package com.philipgurr.data.api

import com.google.gson.annotations.SerializedName

data class UpcModel(
    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("name")
    val name: Map<String, String>
)