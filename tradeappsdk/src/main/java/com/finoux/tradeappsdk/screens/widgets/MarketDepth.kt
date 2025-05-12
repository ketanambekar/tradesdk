import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class Order(
    val price: Float,
    val quantity: Float
)

@Composable
fun MarketDepthWidget(
    buyOrders: List<Order>,
    sellOrders: List<Order>,
    modifier: Modifier = Modifier
) {
    val maxQty = (buyOrders + sellOrders).maxOfOrNull { it.quantity } ?: 1f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x801A1A1A)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Market Depth",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Buy", color = Color.Green, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text("Sell", color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            for (i in 0 until maxOf(buyOrders.size, sellOrders.size)) {
                val buy = buyOrders.getOrNull(i)
                val sell = sellOrders.getOrNull(i)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Buy Side
                    Box(modifier = Modifier.weight(1f)) {
                        DepthBar(
                            quantity = buy?.quantity ?: 0f,
                            maxQuantity = maxQty,
                            color = Color(0xFF388E3C)
                        )
                        OrderInfo(price = buy?.price, quantity = buy?.quantity, isBuy = true)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Sell Side
                    Box(modifier = Modifier.weight(1f)) {
                        DepthBar(
                            quantity = sell?.quantity ?: 0f,
                            maxQuantity = maxQty,
                            color = Color(0xFFD32F2F)
                        )
                        OrderInfo(price = sell?.price, quantity = sell?.quantity, isBuy = false)
                    }
                }
            }
        }
    }
}

@Composable
fun DepthBar(quantity: Float, maxQuantity: Float, color: Color) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
    ) {
        val widthRatio = quantity / maxQuantity
        val barWidth = size.width * widthRatio
        drawRect(
            color = color.copy(alpha = 0.3f),
            size = Size(barWidth, size.height),
            style = Fill
        )
    }
}

@Composable
fun OrderInfo(price: Float?, quantity: Float?, isBuy: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = price?.toString() ?: "-",
            color = if (isBuy) Color.Green else Color.Red,
            fontSize = 12.sp
        )
        Text(
            text = quantity?.toString() ?: "-",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMarketDepthWidget() {
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

    MarketDepthWidget(buyOrders = buys, sellOrders = sells)
}
