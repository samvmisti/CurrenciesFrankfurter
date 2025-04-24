package com.samvmisti.mintostesttask.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samvmisti.mintostesttask.data.model.CurrencyResponse
import com.samvmisti.mintostesttask.data.repository.CurrencyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _currenciesUiState = MutableStateFlow<CurrenciesUiState?>(null)
    val currenciesUiState: StateFlow<CurrenciesUiState?> = _currenciesUiState

    @VisibleForTesting
    internal var allCurrencies = mapOf<String, String>()

    @VisibleForTesting
    internal var currentBase = "EUR"
    private var currentAmount = 1.0

    fun fetchAllData() {
        viewModelScope.launch {
            getAllCurrencies()
            startAutoRefresh()
        }
    }

    @VisibleForTesting
    internal suspend fun getAllCurrencies() {
        repository.getAllCurrencies()
            .onSuccess { allCurrencies = it }
            .onFailure(::updateStateFromError)
    }

    @VisibleForTesting
    internal suspend fun startAutoRefresh() {
        while (true) {
            getRates()
            delay(3000)
        }
    }

    @VisibleForTesting
    internal suspend fun getRates() {
        repository.getRates(currentBase)
            .onSuccess(::updateStateFromResponse)
            .onFailure(::updateStateFromError)
    }

    @VisibleForTesting
    internal fun updateStateFromError(error: Throwable) {
        _currenciesUiState.update {
            it?.copy(error = error.localizedMessage.orEmpty())
        }
    }

    @VisibleForTesting
    internal fun updateStateFromResponse(response: CurrencyResponse) {
        _currenciesUiState.value = CurrenciesUiState.fromResponse(
            currentAmount = currentAmount,
            response = response,
            allCurrencies = allCurrencies,
        )
    }

    fun setBaseCurrency(newBase: String) {
        currentBase = newBase
        viewModelScope.launch { getRates() }
    }

    fun setAmountCurrency(amount: Double) {
        currentAmount = amount
        _currenciesUiState.update {
            CurrenciesUiState(
                selectedCurrency = it?.selectedCurrency?.copy(amount = currentAmount),
                currenciesWithoutSelected = it?.currenciesWithoutSelected?.map { currency ->
                    currency.copy(amount = currentAmount * currency.rate)
                },
            )
        }
    }
}
