package com.finoux.tradesdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

class TradeSdkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isin = intent.getStringExtra("isin") ?: "Unknown"

        setContent {
            MaterialTheme {
                Surface {
                    Text(text = "Stock Details for ISIN: $isin")
                }
            }
        }
    }
}
