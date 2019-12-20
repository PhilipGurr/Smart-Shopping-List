package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.domain.Product

interface UpcRepository {
    suspend fun getProduct(code: String): Product
}