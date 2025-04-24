package com.samvmisti.mintostesttask.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samvmisti.mintostesttask.ui.CurrencyUiModel
import java.util.Locale

@Composable
internal fun LazyItemScope.CurrencyItem(
    baseCode: String,
    uiModel: CurrencyUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .animateItem()
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TitleSmallBlack(uiModel.code)
                TitleSmallBlack(uiModel.description)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End,
            ) {
                val roundedAmount = uiModel.amount.roundTwoDigits()
                Text(
                    text = roundedAmount,
                    style = MaterialTheme.typography.bodyMedium,
                )
                val roundedRte = uiModel.rate.roundTwoDigits()
                Text(
                    text = "1 $baseCode = $roundedRte",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                )
            }
        }
    }
}

private fun Double.roundTwoDigits() =
    String.format(Locale.US, "%.2f", this)
