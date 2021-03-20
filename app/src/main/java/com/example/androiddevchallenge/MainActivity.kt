/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.chrisbanes.accompanist.glide.GlideImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random
import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import com.example.androiddevchallenge.ui.theme.translucentGray
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val dogApi = retrofit.create(DogApi::class.java)

        val window: Window = getWindow()


        setContent {
            MyTheme {
                MyApp {
                    val statusBarColor = MaterialTheme.colors.primary
                    val navigationBarColor = MaterialTheme.colors.primary
                    window.statusBarColor = statusBarColor.toArgb()
                    window.navigationBarColor = navigationBarColor.toArgb()

                    @Suppress("DEPRECATION")
                    if (statusBarColor.luminance() > 0.5f) {
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        @Suppress("DEPRECATION")
                        if (navigationBarColor.luminance() > 0.5f) {
                            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        }
                    }

                    MyAppContent(dogApi)
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        content()
    }
}

@ExperimentalMaterialApi
@Composable
fun MyAppContent(dogApi: DogApi) {
    val navController = rememberNavController()
    NavHost(navController = navController, "dogListScreen") {
        composable("dogListScreen") { DogListScreenScaffold(navController, dogApi) }
        composable("dogDetailsScreen") {
            val dog: Dog = navController.previousBackStackEntry?.arguments?.getParcelable("dog")!!
            DogDetailsScreen(navController, dog)
        }
    }
}

@Composable
fun DogDetailsScreen(navController: NavController, dog: Dog) {
    /*var dog by mutableStateOf<Dog>(Dog("", "", "", "", Sex.Male))
    dogApi.fetchDog(dogId)?.enqueue(object : Callback<DogResult> {
        override fun onResponse(call: Call<DogResult>, response: Response<DogResult>) {
            if (response.isSuccessful) {
                response.body()?.let { dogResult ->
                    var breed = "Mixed"
                    dogResult.breeds?.let {
                        breed = it[0].name
                    }
                    val sex = if (Random.nextInt(0, 2) == 0) Sex.Male else Sex.Female
                    dog = Dog(dogResult.id ?: "", dogResult.url, demoDogs[0].name, breed, sex)
                    Log.d("salala", "dog details result: " + dog)
                }
            }
        }

        override fun onFailure(call: Call<DogResult>, t: Throwable) {
            Log.d("salala", "DogAPI GET dog failure: " + t)
        }
    })*/

    Scaffold(
        /*topBar = {
            TopAppBar(
                title = {
                    Text(text = "KutyApp", color = MaterialTheme.colors.onSurface)
                },
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Navigate back to Dog List Sceen",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable(onClick = { navController.navigateUp() })
                    )
                },
            )
        },*/
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.medium
            ) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite,
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

@ExperimentalMaterialApi
@Composable
fun DogListScreenScaffold(navController: NavController, dogApi: DogApi) {
    val listSize = 10
    var dogs by mutableStateOf(listOf<Dog>())
    
    repeat(listSize) {
        var dog: Dog? = null
        dogApi.fetchDogs()?.enqueue(object : Callback<List<DogResult>> {
            override fun onResponse(call: Call<List<DogResult>>, response: Response<List<DogResult>>) {
                if (response.isSuccessful) {
                    response.body()?.get(0)?.url?.let { url ->
                        val dogResult = response.body()?.get(0)
                        var breed = "Mixed"
                        dogResult?.breeds?.let {
                            breed = it[0].name
                        }
                        val sex = if (Random.nextInt(0, 2) == 0) Sex.Male else Sex.Female
                        dog = Dog(dogResult?.id ?: "", url, demoDogs[it].name, breed, sex)
                    }
                    var dogList = mutableListOf<Dog>()
                    dogList.addAll(dogs)
                    dogList.add(dog!!)
                    dogs = dogList
                }
            }

            override fun onFailure(call: Call<List<DogResult>>, t: Throwable) {
                Log.d("salala", "DogAPI GET dogs failure: " + t)
            }
        })
    }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "KutyApp", color = MaterialTheme.colors.onSurface)
                },
            )
        }
    ) { innerPadding ->
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 90.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.FilterAlt,
                        contentDescription = "Filter Dog List"
                    )
                }
            },
            sheetContent = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                ) {
                    Text(text = "Filters", color = MaterialTheme.colors.onSurface)
                }
            }, sheetPeekHeight = 0.dp
        ){
            DogListScreenContent(
                Modifier
                    .padding(innerPadding)
                    .padding(8.dp),
                navController,
                dogs
            )
        }
    }
}

@Composable
fun DogListScreenContent(modifier: Modifier = Modifier, navController: NavController, dogs: List<Dog>) {
    Box(modifier = modifier) {
        DogList(navController, dogs)
    }
}

@Composable
fun DogList(navController: NavController, dogs: List<Dog>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
        items(dogs) { dog ->
            ListRow(navController, dog)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ListRow(navController: NavController, dog: Dog) {
    val dogId = dog.id
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 0.5.dp, color = Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.currentBackStackEntry?.arguments?.putParcelable("dog", dog)
                    navController.navigate(route = "dogDetailsScreen")
                })
        ) {
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
                    .height(160.dp)
                    .width(200.dp)
                /*.clip(MaterialTheme.shapes.medium)*/,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(dog.name, style = MaterialTheme.typography.subtitle1, color = MaterialTheme.colors.onSurface)
                Text("${dog.breed}", style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.onSurface)
                Text(dog.sex.name, style = MaterialTheme.typography.subtitle2, color = MaterialTheme.colors.onSurface)
            }
        }
    }

}

/*@Preview("ListRow preview", showBackground = true)
@Composable
fun ListRowPreview() {
    MyTheme {
        ListRow(navController = rememberNavController(), dog = demoDogs[0])
    }
}*/

/*@Preview("DogDetails preview")
@Composable
fun DogDetailsPreview() {
    MyTheme {
        DogDetails(dog = demoDogs[0])
    }
}*/

/*Preview("Dog List Screen (Light Theme)", widthDp = 360, heightDp = 640)
@Composable
fun DogListScreenLightPreview() {
    MyTheme {
        DogListScreenContent(navController = rememberNavController(), dogs = demoDogs)
    }
}

@Preview("Dog List Screen (Dark Theme)", widthDp = 360, heightDp = 640)
@Composable
fun DogListScreenDarkPreview() {
    MyTheme(darkTheme = true) {
        DogListScreenContent(navController = rememberNavController(), dogs = demoDogs)
    }
}*/
