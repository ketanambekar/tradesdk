package com.finoux.tradesdk

object TradeSdk {

    private var stockDetailHandler: ((String) -> Unit)? = null

    /**
     * Initializes the SDK. Call this in Application class or MainActivity.
     */
    fun init(stockClickHandler: (isin: String) -> Unit) {
        stockDetailHandler = stockClickHandler
    }

    /**
     * Opens stock details. This is triggered from the main app.
     */
    fun openStockDetails(isin: String) {
        stockDetailHandler?.invoke(isin)
    }
}
