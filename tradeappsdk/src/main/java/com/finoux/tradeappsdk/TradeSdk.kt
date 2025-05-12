package com.finoux.tradesdk

import OrderScreen
import OrderSuccessScreen
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

object TradeSdk {

    private var onCloseCallback: (() -> Unit)? = null

    fun openStockDetails(isin: String) {
        Log.d("TradeSdk", "openStockDetails called with ISIN: $isin")
        _startIsin.value = isin
        _shouldShowHost.value = true
    }

    fun close() {
        Log.d("TradeSdk", "close called, hiding stock details")
        _shouldShowHost.value = false
        onCloseCallback?.invoke()
    }

    @Composable
    fun Host(onClose: () -> Unit) {
        onCloseCallback = onClose

        if (_shouldShowHost.value) {
            Log.d("TradeSdk", "Host composable displayed. Navigating to stock details.")

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "stock_details") {
                composable("stock_details") {
                    val isin = _startIsin.value
                    if (isin != null) {
                        Log.d("TradeSdk", "Displaying StockDetailScreen for ISIN: $isin")
                        StockDetailScreen(
                            isin = isin,
                            navController = navController,
                            onBack = {
                                Log.d("TradeSdk", "Back button pressed. Closing stock details.")
                                close()
                                navController.popBackStack()
                            }
                        )
                    }
                }
                composable("order_screen") {
                    OrderScreen(navController = navController)
                }
                composable("order_confirmation") {
                    OrderSuccessScreen()
                }
            }
        } else {
            Log.d("TradeSdk", "Host composable is not shown.")
        }
    }

    private val _shouldShowHost = mutableStateOf(false)
    private val _startIsin = mutableStateOf<String?>(null)
}
