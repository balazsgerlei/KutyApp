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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.chrisbanes.accompanist.glide.GlideImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val dogApi = retrofit.create(DogApi::class.java)

        setContent {
            MyTheme {
                MyApp {
                    MyAppScaffold(dogApi, moshi)
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

@Composable
fun MyAppScaffold(dogApi: DogApi, moshi: Moshi) {
    val listSize = 10
    val coroutineScope = rememberCoroutineScope()
    var dogs by mutableStateOf(listOf<Dog>())
    repeat(listSize) {
        var dog: Dog? = null
        dogApi.getDog()?.enqueue(object : Callback<List<DogResult>> {
            override fun onResponse(call: Call<List<DogResult>>, response: Response<List<DogResult>>) {
                if (response.isSuccessful) {
                    val url = response.body()?.get(0)?.url?.let { url ->
                        dog = Dog(it, url, "Barky", "Husky", "Male")
                    }
                    var dogList = mutableListOf<Dog>()
                    dogList.addAll(dogs)
                    dogList.add(dog!!)
                    dogs = dogList
                }
            }

            override fun onFailure(call: Call<List<DogResult>>, t: Throwable) {
                // TODO error message
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "KutyApp")
                },
            )
        }
    ) { innerPadding ->
        MyAppContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp),
            dogs
        )
    }
}

@Composable
fun MyAppContent(modifier: Modifier = Modifier, dogs: List<Dog>) {
    Box(modifier = modifier) {
        DogList(dogs)
    }
}

@Composable
fun DogList(dogs: List<Dog>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
        items(dogs) { dog ->
            ListRow(dog)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ListRow(dog: Dog) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { /* Ignore */ })
    ) {
        GlideImage(
            data = dog.imageUrl,
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            contentDescription = "Image of ${dog.name} #${dog.id}",
            fadeIn = true,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(40.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("${dog.name} #${dog.id}", style = MaterialTheme.typography.subtitle1)
            Text("${dog.breed}", style = MaterialTheme.typography.subtitle2)
            Text("${dog.sex}", style = MaterialTheme.typography.subtitle2)
        }
    }
}

/*@Preview("ListRow preview", showBackground = true)
@Composable
fun ListRowPreview() {
    MyTheme {
        ListRow(0)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp {
            MyAppScaffold(null, null)
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp {
            MyAppScaffold()
        }
    }
}*/
