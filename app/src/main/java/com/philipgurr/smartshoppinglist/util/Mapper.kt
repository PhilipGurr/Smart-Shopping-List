package com.philipgurr.smartshoppinglist.util

interface Mapper<T, R> {
    fun map(item: T): R
    fun mapAll(items: List<T>) = items.map(this::map)
}