**DATA**
`CurrencyApi` is using ktor for backend calls.
Currencies are loaded from https://api.frankfurter.app/latest?base=EUR
Currencies names (descriptions) loaded from https://api.frankfurter.dev/v1/currencies
Used `kotlin.Result` to wrap response from `CurrencyApi`.
`CurrencyRepository` is an intermediate entity between remote data source and ViewModel. 

**DI**
Implemented DI with Koin. (AppModule.kt)

**UI**
`CurrencyViewModel` is mapping CurrencyResponse into CurrenciesUiState.
`CurrencyViewModel` is covered with unit tests (partially) using junit4+mockk.
Implemented UI with Jetpack Compose.
UI is listening `currenciesUiState` from `CurrencyViewModel`.