package com.finoux.tradesdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class TradeSdkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isin = intent.getStringExtra("isin") ?: "No ISIN"
        setContent {
            StockDetails(isin = isin)
        }
    }
}

@Composable
fun StockDetails(isin: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Stock Details for ISIN: $isin")
    }
}

@Preview
@Composable
fun PreviewStockDetails() {
    StockDetails(isin = "Sample ISIN")
}
