package  com.samvmisti.mintostesttask.di

import com.samvmisti.mintostesttask.data.api.CurrencyApi
import com.samvmisti.mintostesttask.data.repository.CurrencyRepository
import com.samvmisti.mintostesttask.ui.CurrencyViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CurrencyApi.create() }
    single { CurrencyRepository.create(get()) }
    viewModel { CurrencyViewModel(get()) }
}
