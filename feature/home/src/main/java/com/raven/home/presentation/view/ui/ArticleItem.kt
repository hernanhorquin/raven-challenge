package com.raven.home.presentation.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raven.home.presentation.model.Article

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (Article) -> Unit
) {
    Column(modifier = modifier
        .padding(top = 12.dp)
        .clickable { onClick(article) }
        .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (article.image != null) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    bitmap = article.image.asImageBitmap(),
                    contentDescription = null
                )
                Text(
                    text = article.section.uppercase(),
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                            shape = RectangleShape,
                            alpha = 0.7f
                        )
                        .padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }

        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = article.publishedDate,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color.LightGray
        )
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
}