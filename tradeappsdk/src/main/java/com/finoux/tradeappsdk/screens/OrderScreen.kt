import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(navController: NavController? = null) {
    var quantity by remember { mutableStateOf(1) }
    var price by remember { mutableStateOf(100.0) } // assume default
    val ltp = 100.0
    val stockName = "Zoro Corp"
    val isin = "INE001A01036"
    val exchange = "NSE"

    var sliderConfirmed by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buy Order") },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stockName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("$isin ($exchange)", fontSize = 14.sp)
                    Text("LTP: \u20B9$ltp", fontSize = 14.sp)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Quantity:", modifier = Modifier.weight(1f))
                Button(onClick = { if (quantity > 1) quantity-- }) { Text("-") }
                Spacer(modifier = Modifier.width(8.dp))
                Text("$quantity", modifier = Modifier.width(40.dp), fontSize = 16.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { quantity++ }) { Text("+") }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Price:", modifier = Modifier.weight(1f))
                Button(onClick = { if (price > 1) price -= 1.0 }) { Text("-") }
                Spacer(modifier = Modifier.width(8.dp))
                Text("\u20B9${String.format("%.2f", price)}", modifier = Modifier.width(80.dp), fontSize = 16.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { price += 1.0 }) { Text("+") }
            }

            Text("Total: \u20B9${String.format("%.2f", quantity * price)}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    val navController = rememberNavController()
    OrderScreen(navController)
}