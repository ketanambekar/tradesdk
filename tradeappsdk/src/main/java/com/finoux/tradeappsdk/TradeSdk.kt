package com.finoux.tradesdk

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.runtime.mutableStateOf

object TradeSdk {

    private var _navController: NavHostController? = null
    private val navController get() = _navController!!
    private val _shouldShowHost = mutableStateOf(false)
    private val _startIsin = mutableStateOf<String?>(null)

    private var onCloseCallback: (() -> Unit)? = null

    fun openStockDetails(isin: String) {
        _startIsin.value = isin
        _shouldShowHost.value = true
    }

    fun close() {
        _shouldShowHost.value = false
        onCloseCallback?.invoke()
    }

    @Composable
    fun Host(onClose: () -> Unit) {
        onCloseCallback = onClose

        if (_shouldShowHost.value) {
            val navController = rememberNavController()
            _navController = navController

            NavHost(navController, startDestination = "stock_details") {
                composable("stock_details") {
                    val isin = _startIsin.value
                    if (isin != null) {
                        StockDetailScreen(isin = isin, onBack = { close() })
                    }
                }
            }
        }
    }
}
