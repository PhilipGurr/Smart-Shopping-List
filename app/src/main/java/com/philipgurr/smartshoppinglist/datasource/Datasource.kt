package com.philipgurr.smartshoppinglist.datasource

interface Datasource<T> {
    suspend fun get(name: String): T
    suspend fun getAll(): List<T>
    suspend fun insert(value: T)
    suspend fun insertAll(values: List<T>)
}