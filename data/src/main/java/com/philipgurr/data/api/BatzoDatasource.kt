package com.philipgurr.data.api

import com.philipgurr.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

private const val API_KEY = "" // TODO: Remove key
private const val PREFERRED_LANGUAGE = "en"

class BatzoDatasource @Inject constructor(
    private val batzoService: BatzoService
) : UpcDatasource {
    private lateinit var upcModel: UpcModel

    override suspend fun getProduct(code: String) = withContext(Dispatchers.Default) {
        loadUpcModel(code)
        val name = getNameFromUpcModel()
        Product(name)
    }

    private suspend fun loadUpcModel(code: String) {
        upcModel = try {
            batzoService.getUpcInfoByCode(code, API_KEY)
        } catch (ex: HttpException) {
            throw BarcodeNotFoundException()
        }
    }

    private fun getNameFromUpcModel() =
        upcModel.name[PREFERRED_LANGUAGE] ?: upcModel.name.values.first()
}