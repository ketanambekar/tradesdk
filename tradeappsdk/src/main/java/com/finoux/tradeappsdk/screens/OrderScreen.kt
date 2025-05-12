import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.finoux.tradesdk.StockCard
import com.finoux.tradesdk.loadStockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(navController: NavController? = null) {
    var quantity by remember { mutableStateOf(1) }
    var price by remember { mutableStateOf(100.0) } // assume default
    val ltp = 100.0
    val stockName = "Zoro Corp"
    val isin = "INE001A01036"
    val exchange = "NSE"
    val context = LocalContext.current
    val stockList by remember { mutableStateOf(loadStockData(context)) }
    val stock = stockList.find { it.isin == isin }
    var sliderConfirmed by remember { mutableStateOf(false) }
    val buys = listOf(
        Order(98.5f, 200f),
        Order(98.0f, 150f),
        Order(97.5f, 100f),
        Order(97.0f, 80f),
        Order(96.5f, 60f)
    )
    val sells = listOf(
        Order(99.0f, 180f),
        Order(99.5f, 140f),
        Order(100.0f, 110f),
        Order(100.5f, 90f),
        Order(101.0f, 50f)
    )

    Scaffold(
        containerColor = Color(0xFF0D161F),
        topBar = {
            TopAppBar(
                title = { Text("Buy Order", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back",tint = Color.White)
                    }
                },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0D161F)
                        )
            )
        },
        bottomBar = {
            if (!sliderConfirmed) {
                Button(
                    onClick = { sliderConfirmed = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3BB143))
                ) {
                    Text("Place Buy Order")
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.LightGray, RoundedCornerShape(50))
                        .clickable {
                            // Navigate to confirmation screen
                            navController?.navigate("order_confirmation")
                        }
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("\u2714 Confirmed", color = Color.Green, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)         // respect topBar/bottomBar
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (stock != null) {
                StockCard(stock, onClick = {})
            }

            MarketDepthWidget(buyOrders = buys, sellOrders = sells)

            QuantityPriceSection(
                quantity = quantity,
                price = price,
                onQuantityChange = { quantity = it },
                onPriceChange = { price = it }
            )

//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("Quantity:",color= Color.White, modifier = Modifier.weight(1f))
//                Button(onClick = { if (quantity > 1) quantity-- }) { Text("-") }
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("$quantity", modifier = Modifier.width(40.dp), color= Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
//                Spacer(modifier = Modifier.width(8.dp))
//                Button(onClick = { quantity++ }) { Text("+") }
//            }
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("Price:", color = Color.White, modifier = Modifier.weight(1f))
//                Button(onClick = { if (price > 1) price -= 1.0 }) { Text("-") }
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("\u20B9${String.format("%.2f", price)}", color =  Color.White, modifier = Modifier.width(80.dp), fontSize = 16.sp, textAlign = TextAlign.Center)
//                Spacer(modifier = Modifier.width(8.dp))
//                Button(onClick = { price += 1.0 }) { Text("+") }
//            }

            Text("Total: \u20B9${String.format("%.2f", quantity * price)}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color =  Color.White,)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    val navController = rememberNavController()
    OrderScreen(navController)
}