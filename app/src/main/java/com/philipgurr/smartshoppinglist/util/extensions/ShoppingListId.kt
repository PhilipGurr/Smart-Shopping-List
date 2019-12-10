package com.philipgurr.smartshoppinglist.util.extensions

fun String.toId(): String {
    return hashCode().toUInt().toString()
}