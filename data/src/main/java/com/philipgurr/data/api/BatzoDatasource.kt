package com.philipgurr.data.api

import com.philipgurr.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

private const val API_KEY = "" // TODO: Remove key

class BatzoDatasource @Inject constructor(
    private val batzoService: BatzoService
) : UpcDatasource {

    override suspend fun getProduct(code: String) = withContext(Dispatchers.Default) {
        val model = try {
            batzoService.getUpcInfoByCode(code, API_KEY)
        } catch (ex: HttpException) {
            throw BarcodeNotFoundException()
        }
        val name = model.name["en"] ?: model.name.values.first()
        Product(name)
    }
}