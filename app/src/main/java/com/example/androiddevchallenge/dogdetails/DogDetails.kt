package com.example.androiddevchallenge.dogdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androiddevchallenge.Dog
import com.example.androiddevchallenge.ui.theme.translucentGray
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun DogDetailsScreen(navController: NavController, dog: Dog) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.medium
            ) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface,)
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = MaterialTheme.shapes.medium,
                onClick = { /* TODO */ }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.House,
                        contentDescription = "Adopt this Dog",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    Text("Adopt this Dog", modifier = Modifier.padding(8.dp))
                }
            }
        },
    ) { innerPadding ->
        DogDetailsScreenContent(
            Modifier
                .padding(innerPadding),
            navController = navController,
            dog = dog
        )
    }
}

@Composable
fun DogDetailsScreenContent(modifier: Modifier = Modifier, navController: NavController, dog: Dog) {
    Box(modifier = modifier) {
        DogDetails(navController = navController, dog = dog)
    }
}

@Composable
fun DogDetails(modifier: Modifier = Modifier, navController: NavController, dog: Dog) {
    Column (modifier) {
        Box {
            GlideImage(
                data = dog.imageUrl,
                loading = {
                    Box(Modifier.matchParentSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
                contentDescription = "Image of ${dog.name}",
                fadeIn = true,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            DogDetailsToolbar(modifier = Modifier.fillMaxWidth(), navController = navController)
            DogDetailsProperties(modifier = Modifier.fillMaxHeight().fillMaxWidth(), dog = dog)
        }
    }
}

@Composable
fun DogDetailsToolbar(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .size(36.dp)
                .clip(MaterialTheme.shapes.small)
                .background(
                    color = translucentGray
                )
        ) {
            Icon(
                Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = "Go back to Dog List Sceen"
            )
        }
    }
}

@Composable
fun DogDetailsProperties(modifier: Modifier = Modifier, dog: Dog) {
    Surface(
        modifier = modifier.padding(top = 280.dp),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(dog.name, style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    Icons.Default.Male,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = "Male"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DogPropertiesRow(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), dog = dog)
        }
    }
}

@Composable
fun DogPropertiesRow(modifier: Modifier = Modifier, dog: Dog) {
    Row(modifier.height(IntrinsicSize.Min)) {
        Column(
            Modifier
                .padding(start = 2.dp, end = 2.dp)
                .weight(0.33f)
        ) {
            Text("Breed", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${dog.breed}")
        }
        Divider (
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .width(1.dp)
        )
        Column(
            Modifier
                .padding(start = 2.dp, end = 2.dp)
                .weight(0.33f)
        ) {
            Text("Size", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Medium")
        }
        Divider (
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .width(1.dp)
        )
        Column(
            Modifier
                .padding(start = 2.dp, end = 2.dp)
                .weight(0.33f)
        ) {
            Text("Age", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(8.dp))
            Text("1 year 2 month")
        }
    }
}
