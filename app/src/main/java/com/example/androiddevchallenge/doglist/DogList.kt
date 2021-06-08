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
package com.example.androiddevchallenge.doglist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.Dog
import com.example.androiddevchallenge.DogApi
import com.example.androiddevchallenge.DogResult
import com.example.androiddevchallenge.demoDogs
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalMaterialApi
@Composable
fun DogListScreenScaffold(navController: NavController, dogApi: DogApi) {
    val listSize = 10
    var dogs by remember { mutableStateOf(listOf<Dog>()) }

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
                        dog = Dog(dogResult?.id ?: "", url, demoDogs[it].name, breed, demoDogs[it].sex)
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
            },
            sheetPeekHeight = 0.dp
        ) {
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
            DogListRow(navController, dog)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview("Dog List Screen (Light Theme)", widthDp = 360, heightDp = 640)
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
}
