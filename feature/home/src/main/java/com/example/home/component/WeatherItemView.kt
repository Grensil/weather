package com.example.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.WeatherDto

@Composable
fun WeatherItemView(
    item: WeatherDto,
    subItems: List<WeatherDto>? = emptyList(),
    expanded: Boolean? = null, expandOnClick: ((Boolean) -> Unit)? = null
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = item.region,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
            )

            Text(
                text = item.status,
                fontSize = 16.sp,
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

            Image(
                modifier = Modifier.clickable {
                    expanded?.let { expandOnClick?.invoke(!it) }
                },
                imageVector = if (expanded == true) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = subItems?.isNotEmpty() == true && expanded == true) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                subItems?.forEachIndexed { index, item ->
                    WeatherSubItemView(item = item)
                    if (index < subItems.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherItemViewPreview() {
    WeatherItemView(
        item = WeatherDto(region = "서울", status = "맑음", temperature = 34.4f),
        expanded = true,
        expandOnClick = null
    )
}