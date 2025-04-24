package com.samvmisti.mintostesttask.data.api

import com.samvmisti.mintostesttask.data.model.CurrencyResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal interface CurrencyApi {
    suspend fun fetchRates(base: String): Result<CurrencyResponse>
    suspend fun fetchCurrencies(): Result<Map<String, String>>

    companion object {
        fun create(): CurrencyApi = CurrencyApiImpl()
    }
}

private class CurrencyApiImpl : CurrencyApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun fetchRates(base: String): Result<CurrencyResponse> {
        return try {
            val response: HttpResponse = client.get("https://api.frankfurter.app/latest") {
                url {
                    parameters.append("base", base)
                }
            }
            val data: CurrencyResponse = response.body()
            Result.success(data)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun fetchCurrencies(): Result<Map<String, String>> {
        return try {
            val response: HttpResponse = client.get("https://api.frankfurter.dev/v1/currencies")
            val data: Map<String, String> = response.body()
            Result.success(data)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}

