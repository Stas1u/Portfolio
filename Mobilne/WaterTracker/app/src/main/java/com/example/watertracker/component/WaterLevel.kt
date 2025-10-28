package com.example.watertracker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.watertracker.R
import kotlin.math.min

val BottleImages = arrayOf(
    R.drawable.bottle_empty,
    R.drawable.bottle_1,
    R.drawable.bottle_2,
    R.drawable.bottle_3,
    R.drawable.bottle_4,
    R.drawable.bottle_5,
    R.drawable.bottle_6,
    R.drawable.bottle_7,
    R.drawable.bottle_8,
    R.drawable.bottle_9,
    R.drawable.bottle_10,
    R.drawable.bottle_11,
    R.drawable.bottle_12,
    R.drawable.bottle_13,
    R.drawable.bottle_14,
    R.drawable.bottle_15
)

@Composable
fun WaterLevel(ml: Int, goal: Int, modifier: Modifier = Modifier) {
    val totalImages = BottleImages.size
    val index = min((ml * (totalImages - 1) / goal), totalImages - 1)
    val imageRes = BottleImages[index]

    val shape = RoundedCornerShape(12.dp)
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight / 22 * 10)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = shape,
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Poziom wody",
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}
