import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random

data class DataPoint(val x: Float, val y: Float)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockChartCard()
        }
    }
}

@Composable
fun AreaChart(
    data: List<DataPoint>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF4CAF50),
    fillColor: Brush = Brush.verticalGradient(
        colors = listOf(Color(0x804CAF50), Color.Transparent)
    ),
    axisColor: Color = Color.Gray
) {
    val maxY = data.maxOfOrNull { it.y } ?: 0f
    val minY = data.minOfOrNull { it.y } ?: 0f
    val maxX = data.maxOfOrNull { it.x } ?: 0f
    val minX = data.minOfOrNull { it.x } ?: 0f

    Canvas(modifier = modifier.background(Color(0xFF1A1A1A))) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF2C3E50), Color(0xFF34495E))
            ),
            size = size
        )

        drawGridLines(axisColor)

        drawLine(
            color = axisColor,
            start = Offset(50f, size.height - 50f),
            end = Offset(size.width - 50f, size.height - 50f),
            strokeWidth = 2f
        )
        drawLine(
            color = axisColor,
            start = Offset(50f, 50f),
            end = Offset(50f, size.height - 50f),
            strokeWidth = 2f
        )

        val path = Path()
        val fillPath = Path()

        data.forEachIndexed { index, point ->
            val x = (point.x - minX) / (maxX - minX) * (size.width - 100f) + 50f
            val y = size.height - 50f - (point.y - minY) / (maxY - minY) * (size.height - 100f)

            if (index == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, size.height - 50f)
                fillPath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                fillPath.lineTo(x, y)
            }

            if (index == data.lastIndex) {
                fillPath.lineTo(x, size.height - 50f)
                fillPath.close()
            }
        }

        drawPath(fillPath, brush = fillColor)
        drawPath(path, color = lineColor, style = Stroke(width = 4f))

        // Optional: draw data points
//        data.forEach {
//            val x = (it.x - minX) / (maxX - minX) * (size.width - 100f) + 50f
//            val y = size.height - 50f - (it.y - minY) / (maxY - minY) * (size.height - 100f)
//            drawCircle(color = Color.Red, radius = 5f, center = Offset(x, y))
//        }
    }
}

fun DrawScope.drawGridLines(gridColor: Color) {
    val hLines = 5
    val vLines = 6
    val hSpacing = (size.height - 100f) / hLines
    val vSpacing = (size.width - 100f) / (vLines - 1)

    for (i in 1 until hLines) {
        val y = size.height - 50f - i * hSpacing
        drawLine(
            color = gridColor.copy(alpha = 0.2f),
            start = Offset(50f, y),
            end = Offset(size.width - 50f, y),
            strokeWidth = 1f
        )
    }

    for (i in 0 until vLines) {
        val x = 50f + i * vSpacing
        drawLine(
            color = gridColor.copy(alpha = 0.2f),
            start = Offset(x, 50f),
            end = Offset(x, size.height - 50f),
            strokeWidth = 1f
        )
    }
}

@Composable
fun StockChartCard() {
    val timeRanges = listOf("1D", "1M", "1Y", "5Y", "All")
    var selectedRange by remember { mutableStateOf("1D") }

    val data = remember(selectedRange) {
        when (selectedRange) {
            "1D" -> List(6) { DataPoint(it.toFloat(), 100f + Random.nextFloat() * 10f) }
            "1M" -> List(30) { DataPoint(it.toFloat(), 100f + Random.nextFloat() * 50f) }
            "1Y" -> List(12) { DataPoint(it.toFloat(), 150f + it * 20 + Random.nextFloat() * 50f) }
            "5Y" -> List(60) { DataPoint(it.toFloat(), 100f + it * 10 + Random.nextFloat() * 100f) }
            "All" -> List(100) { DataPoint(it.toFloat(), 80f + it * 8 + Random.nextFloat() * 200f) }
            else -> emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Stock Trend",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            timeRanges.forEach { label ->
                Button(
                    onClick = { selectedRange = label },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRange == label) Color(0xFF4CAF50) else Color.DarkGray,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.defaultMinSize(minHeight = 1.dp)
                ) {
                    Text(text = label, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AreaChart(
            data = data,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStockChart() {
    StockChartCard()
}
