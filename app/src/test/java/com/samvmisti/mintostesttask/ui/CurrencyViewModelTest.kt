package com.samvmisti.mintostesttask.ui

import com.samvmisti.mintostesttask.MainDispatcherRule
import com.samvmisti.mintostesttask.data.model.CurrencyResponse
import com.samvmisti.mintostesttask.data.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class CurrencyViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: CurrencyRepository = mockk()
    private val vm: CurrencyViewModel
        get() = CurrencyViewModel(repository)

    @Test
    fun `viewmodel when created should fetch all data`() = runTest {
        val vm = spyk(vm) {
            coEvery { getAllCurrencies() } just runs
            coEvery { startAutoRefresh() } just runs
        }
        vm.fetchAllData()
        coVerify { vm.getAllCurrencies() }
        coVerify { vm.startAutoRefresh() }
    }

    @Test
    fun `getAllCurrencies updates currencies if success`() = runTest {
        val currencies = mapOf("EUR" to "euro")
        coEvery { repository.getAllCurrencies() } returns Result.success(currencies)
        val vm = vm
        vm.getAllCurrencies()
        coVerify { repository.getAllCurrencies() }
        assert(vm.allCurrencies == currencies)
    }

    @Test
    fun `getAllCurrencies updates error if failure`() = runTest {
        val error = mockk<Throwable>()
        coEvery { repository.getAllCurrencies() } returns Result.failure(error)
        val vm = spyk(vm) {
            coEvery { updateStateFromError(any()) } just runs
        }
        vm.getAllCurrencies()
        coVerify { repository.getAllCurrencies() }
        coVerify { vm.updateStateFromError(error) }
    }

    @Test
    fun `getRates updates currencies if success`() = runTest {
        val currencies = mockk<CurrencyResponse>()
        coEvery { repository.getRates(any()) } returns Result.success(currencies)
        val vm = spyk(vm) {
            every { updateStateFromResponse(any()) } just runs
        }
        vm.getRates()
        coVerify { repository.getRates("EUR") }
        verify { vm.updateStateFromResponse(currencies) }
    }

    @Test
    fun `getRates updates error if failure`() = runTest {
        val error = mockk<Throwable>()
        coEvery { repository.getRates(any()) } returns Result.failure(error)
        val vm = spyk(vm) {
            coEvery { updateStateFromError(any()) } just runs
        }
        vm.getRates()
        coVerify { repository.getRates("EUR") }
        coVerify { vm.updateStateFromError(error) }
    }
}