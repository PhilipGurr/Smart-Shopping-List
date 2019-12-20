package com.philipgurr.smartshoppinglist.repository

import com.philipgurr.smartshoppinglist.data.api.UpcDatasource
import javax.inject.Inject

class StandardUpcRepository @Inject constructor(
    private val datasource: UpcDatasource
) : UpcRepository {
    override suspend fun getProduct(code: String) = datasource.getProduct(code)
}