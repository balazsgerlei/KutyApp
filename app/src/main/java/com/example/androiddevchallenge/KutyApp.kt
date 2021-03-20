package com.example.androiddevchallenge

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.dogdetails.DogDetailsScreen
import com.example.androiddevchallenge.doglist.DogListScreenScaffold

@Composable
fun KutyApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        content()
    }
}

@ExperimentalMaterialApi
@Composable
fun KutyAppContent(dogApi: DogApi) {
    val navController = rememberNavController()
    NavHost(navController = navController, "dogListScreen") {
        composable("dogListScreen") { DogListScreenScaffold(navController, dogApi) }
        composable("dogDetailsScreen") {
            val dog: Dog = navController.previousBackStackEntry?.arguments?.getParcelable("dog")!!
            DogDetailsScreen(navController, dog)
        }
    }
}
