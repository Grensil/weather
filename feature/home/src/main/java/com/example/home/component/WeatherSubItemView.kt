package com.example.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.WeatherDto

@Composable
fun WeatherSubItemView(item: WeatherDto) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = item.region,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
        )

        Text(
            text = item.status,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
        )

        Text(
            text = "(${item.temperature}\u00B0C)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.weight(1f))


    }
}

