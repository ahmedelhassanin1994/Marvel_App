package com.example.marvel_app.presentation.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.marvel_app.R
import com.example.marvel_app.core.NetworkResult
import com.example.marvel_app.core.common.CoilImage
import com.example.marvel_app.core.resources.BlackTransparent
import com.example.marvel_app.core.resources.MovieDetailsTextColor
import com.example.marvel_app.core.resources.MoviePrimaryBackgroundColor
import com.example.marvel_app.data.responeses.Results
import com.example.marvel_app.presentation.home.MarvelGridList

@Composable
fun DetailsScreen(navController: NavController, id: String?, viewModel: DetailsViewModel = hiltViewModel(),modifier: Modifier = Modifier){
    val result by viewModel.result.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MoviePrimaryBackgroundColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                  viewModel.getCharacters_Details(id.toString())
                }

                is NetworkResult.Success -> {
                    DetailsSuccess(result.data?.data?.results!!.first())

                }

                is NetworkResult.Loading -> {
                    Box(modifier.fillMaxSize()) {
                        CircularProgressIndicator(

                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 24.dp)
                        )
                    }
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ")
                }
            }

        }

}

@Composable
fun DetailsSuccess(characterResult: Results, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = true)
            .background(MoviePrimaryBackgroundColor)
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                CoilImage(
                    imageUrl = "${characterResult.thumbnail?.path.toString()}/portrait_medium.jpg",
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    BlackTransparent,
                                    MoviePrimaryBackgroundColor
                                )
                            )
                        )
                )


            }

            characterResult.description?.let {
                InfoItem(
                    title = "Description",
                    subtitle = it,
                    modifier = Modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp)
                )
            }
//
//            val notAvailable = stringResource(R.string.movies_details_not_available)
//            val director = viewState.credits.crew.mapCrew(DIRECTOR)
//                .ifBlank { notAvailable }
//            val writer = viewState.credits.crew.mapCrew(WRITER, SCREENPLAY)
//                .ifBlank { notAvailable }
//            val production = viewState.details.production.joinToString(",\n") { it.name }
//                .ifBlank { notAvailable }
//
//            InfoRow(
//                data = listOf(
//                    stringResource(R.string.movies_details_director) to director,
//                    stringResource(R.string.movies_details_screenplay) to writer,
//                    stringResource(R.string.movies_details_production) to production
//                ),
//                state = rememberLazyListState()
//            )
//            InfoItem(
//                title = stringResource(id = R.string.movies_details_cast),
//                subtitle = "",
//                modifier = Modifier.padding(horizontal = 16.dp)
//            )

        }
    }
}


@Composable
fun InfoItem(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MovieDetailsTextColor,
            modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
        )
        if(subtitle.isNotBlank()){
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                color = Color.White,
            )
        }
    }
}