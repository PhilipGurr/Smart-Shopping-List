package com.philipgurr.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BatzoService {
    @GET("products")
    suspend fun getUpcInfoByCode(@Query("barcode") barcode: String, @Query("key") key: String): UpcModel
}