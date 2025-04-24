package com.samvmisti.mintostesttask.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
internal fun TitleSmallWhite(text: String) {
    TitleSmall(
        color = Color.White,
        text = text,
    )
}

@Composable
internal fun TitleSmallBlack(text: String) {
    TitleSmall(
        color = Color.Black,
        text = text,
    )
}

@Composable
private fun TitleSmall(color: Color, text: String) {
    Text(
        color = color,
        text = text,
        style = MaterialTheme.typography.titleSmall,
    )
}