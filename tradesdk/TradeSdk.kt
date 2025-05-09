package com.finoux.tradesdk
import android.content.Context
import android.content.Intent

object TradeSdk {
    fun openStockDetails(context: Context, isin: String) {
        val intent = Intent(context, TradeSdkActivity::class.java)
        intent.putExtra("isin", isin)
        context.startActivity(intent)
    }
}
