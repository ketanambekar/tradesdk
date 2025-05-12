import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuantityPriceSection(
    quantity: Int,
    price: Double,
    onQuantityChange: (Int) -> Unit,
    onPriceChange: (Double) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A).copy(alpha = 0.8f)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Quantity Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Quantity:", color = Color.White, fontSize = 16.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
//                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(50),
                    ) {
                        Text("-",color = Color.White, fontSize = 16.sp,)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "$quantity",
                        modifier = Modifier.width(80.dp),
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onQuantityChange(quantity + 1) },
//                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("+",color = Color.White, fontSize = 16.sp,)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Price:", color = Color.White, fontSize = 16.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { if (price > 1.0) onPriceChange(price - 1.0) },
//                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("-",color = Color.White, fontSize = 16.sp,)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "\u20B9${String.format("%.2f", price)}",
                        modifier = Modifier.width(80.dp),
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onPriceChange(price + 1.0) },
//                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("+",color = Color.White, fontSize = 16.sp,)
                    }
                }
            }
        }
    }
}
