package com.philipgurr.smartshoppinglist.repository

interface Repository<T> {
    fun get(): T
    fun getAll(): List<T>
    fun add(value: T)
    fun addAll(values: List<T>)
}