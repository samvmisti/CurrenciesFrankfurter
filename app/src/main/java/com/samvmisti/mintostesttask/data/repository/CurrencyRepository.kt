package com.samvmisti.mintostesttask.data.repository

import com.samvmisti.mintostesttask.data.api.CurrencyApi
import com.samvmisti.mintostesttask.data.model.CurrencyResponse

internal interface CurrencyRepository {
    suspend fun getRates(base: String): Result<CurrencyResponse>
    suspend fun getAllCurrencies(): Result<Map<String, String>>

    companion object {
        fun create(api: CurrencyApi): CurrencyRepository = CurrencyRepositoryImpl(api)
    }
}

private class CurrencyRepositoryImpl(private val api: CurrencyApi) : CurrencyRepository {
    override suspend fun getRates(base: String): Result<CurrencyResponse> = api.fetchRates(
        base = base,
    )

    override suspend fun getAllCurrencies(): Result<Map<String, String>> = api.fetchCurrencies()
}
