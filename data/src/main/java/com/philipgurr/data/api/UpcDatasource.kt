package com.philipgurr.data.api

import com.philipgurr.domain.Product

interface UpcDatasource {
    suspend fun getProduct(code: String): Product
}