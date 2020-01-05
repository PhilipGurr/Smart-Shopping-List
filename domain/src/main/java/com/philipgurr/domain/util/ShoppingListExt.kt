package com.philipgurr.domain.util

fun String.toId(): String {
    return hashCode().toUInt().toString()
}