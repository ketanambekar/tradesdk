import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class StockStats(
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Long,
    val yearHigh: Float,
    val yearLow: Float
)

@Composable
fun StockInfoWidget(
    stats: StockStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Stock Details",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            StockRow(label = "Open", value = stats.open)
            StockRow(label = "High", value = stats.high)
            StockRow(label = "Low", value = stats.low)
            StockRow(label = "Close", value = stats.close)
            StockRow(label = "Volume", value = stats.volume.toString())
            StockRow(label = "52W High", value = stats.yearHigh)
            StockRow(label = "52W Low", value = stats.yearLow)
        }
    }
}

@Composable
fun StockRow(label: String, value: Any) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.LightGray, fontSize = 14.sp)
        Text(text = value.toString(), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, widthDp = 360, heightDp = 600)
@Composable
fun PreviewStockInfoWidget() {
    val sampleStats = StockStats(
        open = 425.6f,
        high = 450.0f,
        low = 420.4f,
        close = 440.2f,
        volume = 12456879,
        yearHigh = 550.0f,
        yearLow = 310.0f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        StockInfoWidget(stats = sampleStats)
    }
}