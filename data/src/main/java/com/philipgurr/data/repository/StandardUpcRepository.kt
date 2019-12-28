package com.philipgurr.data.repository

import com.philipgurr.data.api.UpcDatasource
import com.philipgurr.domain.repository.UpcRepository
import javax.inject.Inject

class StandardUpcRepository @Inject constructor(
    private val datasource: UpcDatasource
) : UpcRepository {
    override suspend fun getProduct(code: String) = datasource.getProduct(code)
}