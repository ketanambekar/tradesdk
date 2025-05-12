package com.finoux.tradesdk
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.finoux.tradeappsdk.R
import com.finoux.tradeappsdk.data.StockInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    name = "Stock Details Preview",
    device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun StockDetailScreen(isin: String?, onBack: () -> Unit = {}) {
    val navController = rememberNavController()
    val isin = "INE001A01036"
    val context = LocalContext.current
    // Store the stock data in memory for efficient retrieval
    val stockList by remember { mutableStateOf(loadStockData(context)) }

    // Find the stock from the list using the provided ISIN
    val stock = stockList.find { it.isin == isin }

    if (stock == null) {
        // If stock is not found, display a message
        Text("Stock not found for ISIN: $isin")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentPadding = PaddingValues(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006400)
                    ),
                    onClick = {
                        navController?.navigate("order_screen")
                    }
                ) {
                    Text("Buy Stock")
                }
            }
        },
        content = { innerPadding ->
            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(10.dp),  // Padding around the Card
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(innerPadding)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // First two Text elements
                    Text(
                        text = stock.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${stock.isin} (${stock.exchange})",
                        fontSize = 16.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "LTP: ${stock.lastTradePrice}",
                            fontSize = 16.sp
                        )
                        Text(
                            text = " (+1.20)", // This is the change or any other value you'd like to show
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    )
}

// Function to load stock data from a raw resource file
fun loadStockData(context: Context): List<StockInfo> {
    val inputStream = context.resources.openRawResource(R.raw.stocks)
    val json = inputStream.bufferedReader().use { it.readText() }
    val gson = Gson()
    val listType = object : TypeToken<List<StockInfo>>() {}.type
    return gson.fromJson(json, listType)
}
