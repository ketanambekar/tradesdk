package com.finoux.tradesdk

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailScreen(isin: String) {
    // Mock data for example
    val stockName = remember { "HDFC Bank Ltd." }
    val exchange = remember { "NSE" }
    val lastTradePrice = remember { "₹2,150.50" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Stock Details")
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
                    onClick = {
                        // Handle action here
                    }
                ) {
                    Text("Buy Stock")
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Name: $stockName", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("ISIN: $isin", fontSize = 16.sp)
                Text("Exchange: $exchange", fontSize = 16.sp)
                Text("Last Trade Price: $lastTradePrice", fontSize = 16.sp)
            }
        }
    )
}
