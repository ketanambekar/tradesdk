package com.yourcompany.tradesdk

import android.content.Context
import android.widget.Toast

object TradeSdk {
    fun showTradeDialog(context: Context, message: String) {
        Toast.makeText(context, "TradeSDK: $message", Toast.LENGTH_LONG).show()
    }
}
