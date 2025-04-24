package com.samvmisti.mintostesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samvmisti.mintostesttask.di.appModule
import com.samvmisti.mintostesttask.ui.components.TopBar
import com.samvmisti.mintostesttask.ui.screen.MainScreen
import com.samvmisti.mintostesttask.ui.theme.CurrencyConverterTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            CurrencyConverterTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}