package com.finoux.tradesdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class TradeSdkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isin = intent.getStringExtra("isin") ?: "Unknown ISIN"

        setContent {
            StockDetailScreen(isin)
        }
    }
}

@Composable
fun StockDetailScreen(isin: String) {
    Text(text = "Details for ISIN: $isin")
}
