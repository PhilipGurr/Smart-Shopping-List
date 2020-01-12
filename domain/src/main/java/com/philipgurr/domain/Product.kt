package com.philipgurr.domain


data class Product(
    val name: String = "",
    val completed: Boolean = false
) {
    fun displayName(): String {
        val words = name.split(" ")
        return if (words.size > 4) {
            words.joinToString(limit = 4, separator = " ", truncated = "")
        } else {
            words.joinToString(separator = " ")
        }
    }
}
