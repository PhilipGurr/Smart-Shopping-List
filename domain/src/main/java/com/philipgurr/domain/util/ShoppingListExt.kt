package com.philipgurr.domain.util

import com.philipgurr.domain.ShoppingList

fun String.toId(): String {
    return hashCode().toUInt().toString()
}

fun ShoppingList.completedProducts() = products.filter { it.completed }
fun ShoppingList.getSortedProducts() = products.sortedByDescending { it.created }