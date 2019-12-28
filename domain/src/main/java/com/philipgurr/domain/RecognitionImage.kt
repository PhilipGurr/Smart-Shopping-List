package com.philipgurr.domain

data class RecognitionImage(
    val width: Int,
    val height: Int,
    val buffer: ByteArray,
    val rotation: Int
)