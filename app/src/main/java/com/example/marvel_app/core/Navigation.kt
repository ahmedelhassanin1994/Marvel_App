package com.example.marvel_app.core

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel_app.core.common.JsonNavType
import com.example.marvel_app.presentation.details_screen.DetailsScreen
import com.example.marvel_app.presentation.home.HomeScreen
import com.google.gson.Gson

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController =navController , startDestination = "Home"  ){

        composable(route = "Home") {
            HomeScreen(navController = navController )
        }

        composable(route = "DetailsScreen?id={id}",
            arguments = listOf(
                navArgument(name = "id"){
                    type= NavType.StringType
                    defaultValue=""
                },
            )

            ) {backStack  ->
            DetailsScreen(navController = navController,id = backStack.arguments?.getString("id") )

        }


    }
}

