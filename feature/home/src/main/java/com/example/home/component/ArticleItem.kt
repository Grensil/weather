package com.example.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.ArticleDto
import com.example.domain.SourceDto

@Composable
fun ArticleItem(item: ArticleDto) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentWidth().background(Color.LightGray,
        RoundedCornerShape(12.dp)
    ).padding(12.dp)) {

        if(!item.author.isNullOrEmpty()) {
            Text(text = item.author?:"",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Text(text = "${item.title}",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)

        Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())

        Row(verticalAlignment = Alignment.CenterVertically) {

            if(!item.urlToImage.isNullOrEmpty()) {
                AsyncImage(modifier = Modifier.size(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray),
                    model = item.urlToImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = "article image")

                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Top) {

                if(!item.content.isNullOrEmpty()) {
                    Text(text = "${item.content}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis)

                    Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())
                }

                if(!item.description.isNullOrEmpty()) {
                    Text(text = "${item.description}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
    val fakeItem = ArticleDto(
        source = SourceDto(id = null, name = "PRNewswire"),
        author = "Author",
        title = "EchoStar Announces Spectrum Sale and Commercial Agreement with SpaceX - PR Newswire",
        description = "/PRNewswire/ -- EchoStar has entered into a definitive agreement with SpaceX to sell the company's AWS-4 and H-block spectrum licenses for approximately $17...",
        url = "https://www.prnewswire.com/news-releases/echostar-announces-spectrum-sale-and-commercial-agreement-with-spacex-302548650.html",
        urlToImage = "https://mma.prnewswire.com/media/2309198/ECHOSTAR_LOGO_RED.jpg?p=facebook",
        publishedAt = "2025-09-08T10:33:00Z",
        content = "EchoStar to sell full portfolio of AWS-4 and H-block spectrum licenses to SpaceX."
    )
    ArticleItem(fakeItem)
}