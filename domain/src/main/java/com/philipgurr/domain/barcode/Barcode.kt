package com.philipgurr.domain.barcode

data class Barcode(
    val rawValue: String,
    val boundingBox: BarcodeBoundingBox
)