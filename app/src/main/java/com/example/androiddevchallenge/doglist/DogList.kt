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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androiddevchallenge.Dog
import com.example.androiddevchallenge.DogApi
import com.example.androiddevchallenge.DogResult
import com.example.androiddevchallenge.Sex
import com.example.androiddevchallenge.demoDogs
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

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
            DogListRow(navController, dog)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}