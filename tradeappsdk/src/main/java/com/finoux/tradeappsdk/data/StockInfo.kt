package com.finoux.tradeappsdk.data

data class StockInfo(
    val isin: String,
    val name: String,
    val exchange: String,
    val lastTradePrice: String,
    val change: String
)
