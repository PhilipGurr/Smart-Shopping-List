package com.philipgurr.smartshoppinglist.repository

interface Repository<T> {
    suspend fun get(name: String): T
    suspend fun getAll(): List<T>
    suspend fun add(value: T)
    suspend fun addAll(values: List<T>)
}