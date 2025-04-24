package com.samvmisti.mintostesttask.ui

import com.samvmisti.mintostesttask.data.model.CurrencyResponse

internal data class CurrencyUiModel(
    val code: String,
    val description: String,
    val rate: Double,
    val amount: Double,
    val isSelected: Boolean,
)

internal data class CurrenciesUiState(
    val error: String? = null,
    val selectedCurrency: CurrencyUiModel?,
    val currenciesWithoutSelected: List<CurrencyUiModel>?,
) {
    companion object {
        fun fromResponse(
            currentAmount: Double,
            response: CurrencyResponse,
            allCurrencies: Map<String, String>,
        ): CurrenciesUiState {
            val baseModel = response.base.let {
                CurrencyUiModel(
                    code = it,
                    description = allCurrencies[it].orEmpty(),
                    rate = 0.0,
                    amount = currentAmount,
                    isSelected = true,
                )
            }
            val otherModels = response.rates.map { (code, rate) ->
                CurrencyUiModel(
                    code = code,
                    description = allCurrencies[code].orEmpty(),
                    rate = rate,
                    amount = currentAmount * rate,
                    isSelected = false,
                )
            }
            return CurrenciesUiState(
                selectedCurrency = baseModel,
                currenciesWithoutSelected = otherModels,
            )
        }
    }
}
