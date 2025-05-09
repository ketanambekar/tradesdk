package com.finoux.tradesdk

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StockListScreen() {
    Text("This is the Stock List Screen (from SDK)")
}

@Composable
fun StockDetailScreen(isin: String) {
    Text("Stock Detail Screen for ISIN: $isin")
}
