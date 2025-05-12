package com.finoux.tradesdk
import StockChartCard
import StockInfoWidget
import StockStats
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finoux.tradeappsdk.R
import com.finoux.tradeappsdk.data.StockInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)

@Composable
fun StockDetailScreen(
    isin: String = "INE001A01036",
    navController: NavHostController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val stockList by remember { mutableStateOf(loadStockData(context)) }
    val stock = stockList.find { it.isin == isin }

    if (stock == null) {
        Text("Stock not found for ISIN: $isin")
        return
    }
    val stats = StockStats(
        open = 425.60f,
        high = 450.00f,
        low = 420.40f,
        close = 440.20f,
        volume = 12456879,
        yearHigh = 550.00f,
        yearLow = 310.00f
    )
    Scaffold(
        containerColor = Color(0xFF0D161F),
        topBar = {
            TopAppBar(
                title = { Text("Stock Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D161F)
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
//                contentPadding = PaddingValues(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3BB143)),
                    onClick = {
                        navController.navigate("order_screen")
                    }
                ) {
                    Text("Buy Stock")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)         // respect topBar/bottomBar
                .padding(top = 16.dp)          // additional top padding
        ) {
            StockCard(stock, onClick = {})
            StockChartCard()
            StockInfoWidget(stats = stats)
        }
    }
}

fun loadStockData(context: Context): List<StockInfo> {
    val inputStream = context.resources.openRawResource(R.raw.stocks)
    val json = inputStream.bufferedReader().use { it.readText() }
    val gson = Gson()
    val listType = object : TypeToken<List<StockInfo>>() {}.type
    return gson.fromJson(json, listType)
}


@Composable
fun StockCard(stock: StockInfo, onClick: () -> Unit) {
    val changeColor = if (stock.change.startsWith("+")) Color.Green else Color.Red

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .background(
                color = Color.Gray,
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onClick()
                try {
                    Log.d("TradeSdk", "Opening stock details for ISIN: ${stock.isin}")
                    TradeSdk.openStockDetails(stock.isin)
                } catch (e: Exception) {
                    Log.e("TradeSdk", "Error opening stock details: ${e.message}")
                }
            }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stock icon/avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stock.name.first().uppercase(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(stock.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text("ISIN: ${stock.isin}", color = Color.White, fontSize = 12.sp)
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("LTP: ₹${stock.lastTradePrice}", color = Color.White, fontSize = 12.sp)
                Text("Change: ${stock.change}", color = changeColor, fontSize = 12.sp)
            }
        }
    }
}
