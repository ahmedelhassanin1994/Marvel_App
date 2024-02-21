package com.example.marvel_app.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import arrow.core.computations.result
import com.example.marvel_app.core.NetworkResult
import com.example.marvel_app.core.resources.MoviePrimaryBackgroundColor
import com.example.marvel_app.core.resources.PurpleGrey80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(),modifier: Modifier = Modifier,navController: NavController) {
    val result by viewModel.result.collectAsState()
    val result_search = viewModel.list_search
    var text by remember{ mutableStateOf("") }
    var IsSearch by remember{ mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MoviePrimaryBackgroundColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            OutlinedTextField(
                value = text,
                textStyle = TextStyle.Default.copy(color = PurpleGrey80),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor =PurpleGrey80,
                    unfocusedBorderColor = PurpleGrey80,
                    focusedLabelColor = PurpleGrey80,
                    cursorColor = PurpleGrey80
                ),
                onValueChange = {
                      text=it
                    if (it.length>2){
                        IsSearch=true
                        viewModel.addSearchedFOrItemsToSearchedList(text)
                    }else{
                        IsSearch=false

                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder ={ Text(text = "Character", color = PurpleGrey80)},
                label = { Text(text = "search", color = PurpleGrey80)},



            )
            when (result) {
                is NetworkResult.Initial -> {
                    Text(text = "Search for a character")
                }

                is NetworkResult.Success -> {

                    if (IsSearch){
                        MarvelGridList(
                            list = result_search,
                            navController=navController
                        )
                    }else{
                        MarvelGridList(
                            list = result.data?.data?.results!!,
                            navController=navController

                        )
                    }

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





