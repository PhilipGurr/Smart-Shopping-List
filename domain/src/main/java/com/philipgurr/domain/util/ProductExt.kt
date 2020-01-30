package com.philipgurr.domain.util

import com.philipgurr.domain.Product

private const val MAX_WORDS_IN_NAME = 4

fun Product.displayName(): String {
    val words = name.split(" ")
    return words.joinToString(limit = MAX_WORDS_IN_NAME, separator = " ", truncated = "").trim()
}