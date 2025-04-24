package com.samvmisti.mintostesttask.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.samvmisti.mintostesttask.ui.CurrencyUiModel

@Composable
internal fun CurrencyItemSelected(
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    uiModel: CurrencyUiModel,
    onBaseAmountChanged: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                focusRequester.requestFocus()
                keyboardController?.show()
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.background(Color(0xFF006666)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TitleSmallWhite(text = uiModel.code)
                TitleSmallWhite(text = uiModel.description)
            }
            Spacer(modifier = Modifier.weight(1f))
            BasicTextField(
                value = uiModel.amount.toString(),
                onValueChange = onBaseAmountChanged,
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = Color.White,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(min = 80.dp)
                    .focusRequester(focusRequester),
                singleLine = true,
                cursorBrush = SolidColor(Color.White)
            )
        }
    }
}
