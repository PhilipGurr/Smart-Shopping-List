package com.philipgurr.smartshoppinglist.data.api

import com.philipgurr.smartshoppinglist.domain.Product

interface UpcDatasource {
    suspend fun getProduct(code: String): Product
}