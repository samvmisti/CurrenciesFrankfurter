package com.samvmisti.mintostesttask.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class CurrencyResponse(
    val amount: Double,
    val base: String,
    val rates: Map<String, Double>,
)
