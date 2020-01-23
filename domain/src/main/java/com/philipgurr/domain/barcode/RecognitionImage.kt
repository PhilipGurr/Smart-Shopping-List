package com.philipgurr.domain.barcode

data class RecognitionImage(
    val width: Int,
    val height: Int,
    val buffer: ByteArray,
    val rotation: Int
)