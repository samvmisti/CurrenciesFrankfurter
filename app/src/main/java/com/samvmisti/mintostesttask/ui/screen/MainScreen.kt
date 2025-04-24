package com.samvmisti.mintostesttask.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.samvmisti.mintostesttask.ui.CurrencyViewModel
import com.samvmisti.mintostesttask.ui.components.CurrencyItem
import com.samvmisti.mintostesttask.ui.components.CurrencyItemSelected
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MainScreen(viewModel: CurrencyViewModel = koinViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchAllData()
    }

    val state by viewModel.currenciesUiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val baseItem = state?.selectedCurrency ?: return
    val otherItems = state?.currenciesWithoutSelected ?: return
    val error = state?.error

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .animateContentSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (error != null) {
            Text(text = error)
            return
        }
        CurrencyItemSelected(
            keyboardController = keyboardController,
            focusRequester = focusRequester,
            uiModel = baseItem,
            onBaseAmountChanged = {
                viewModel.setAmountCurrency(it.toDoubleOrNull() ?: 1.0)
            },
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 80.dp)
        ) {
            items(otherItems, key = { it.code }) {
                CurrencyItem(
                    baseCode = baseItem.code,
                    uiModel = it,
                    onClick = {
                        viewModel.setBaseCurrency(it.code)
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    },
                )
            }
        }
    }
}
