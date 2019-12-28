package com.philipgurr.data.di.modules

import com.philipgurr.data.api.BatzoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class ApiModule {
    @Provides
    @Named("baseUrl")
    fun baseUrl() = "https://www.batzo.net/api/v1/"

    @Provides
    fun provideApiService(@Named("baseUrl") url: String): BatzoService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BatzoService::class.java)
    }
}