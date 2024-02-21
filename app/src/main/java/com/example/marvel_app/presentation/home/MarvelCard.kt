package com.example.marvel_app.presentation.home

import androidx.compose.material3.Text



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.marvel_app.core.common.CoilImage
import com.example.marvel_app.core.resources.BlackTransparent
import com.example.marvel_app.core.resources.cardShape


@Composable
fun MovieCard(
    title: String,
    rating: Float,
    imageUrl: String,
    onClickListener: () -> Unit,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier = modifier
            .clip(cardShape)
            .fillMaxWidth()
            .height(172.dp)
            .clickable {
                onClickListener()
            }
    ) {

        // Create references for the composables to constrain
        val (imageRef, titleRef, ratingRef) = createRefs()

        CoilImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .constrainAs(imageRef) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                    )
                }
                .fillMaxWidth()
                .fillMaxHeight()
        )

        MovieRating(rating = "%.1f".format(rating),
            modifier = Modifier
                .padding(start = 12.dp)
                .constrainAs(ratingRef) {
                    top.linkTo(imageRef.top, margin = 10.dp)
                })

        MovieTitle(
            title = title,
            modifier = Modifier
                .constrainAs(titleRef) {
                    bottom.linkTo(imageRef.bottom)
                }
        )
    }
}

@Composable
fun MovieTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        BlackTransparent
                    ),
                )
            )
            .padding(start = 12.dp, end = 4.dp, bottom = 10.dp, top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = Color.White,
            maxLines = 1,
            overflow = Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
        )

    }
}

@Composable
fun MovieRating(
    rating: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(BlackTransparent, RoundedCornerShape(16.dp))
            .padding(start = 4.dp, end = 6.dp, top = 2.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Image(
//            modifier = Modifier.size(14.dp),
//            contentScale = ContentScale.Inside,
//            painter = painterResource(id = R.drawable.ic_star),
//            contentDescription = "rating",
//        )
        Text(
            text = rating,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}





