package com.finoux.tradesdk
import OrderScreen
import OrderSuccessScreen
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.compose.*

object TradeSdk {

    private var onCloseCallback: (() -> Unit)? = null  // Listener (callback) to be set by the parent app

    // Expose a public close function that can be called from the parent app to close the SDK
    fun closeSdk() {
        Log.d("TradeSdk", "closeSdk called, hiding stock details")
        _shouldShowHost.value = false  // Hide the SDK's view
        onCloseCallback?.invoke()  // Trigger the callback to notify the parent app or any listener
    }

    fun openStockDetails(isin: String) {
        Log.d("TradeSdk", "openStockDetails called with ISIN: $isin")
        _startIsin.value = isin
        _shouldShowHost.value = true
    }

    @Composable
    fun Host(onClose: () -> Unit) {
        onCloseCallback = onClose  // Set the listener that will handle SDK close event

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
                                closeSdk()  // Call closeSdk() to notify that the SDK should be closed
                                navController.popBackStack()  // Go back to the previous screen
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
