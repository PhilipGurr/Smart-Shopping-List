package com.philipgurr.domain.repository

import com.philipgurr.domain.Product

interface UpcRepository {
    suspend fun getProduct(code: String): Product
}