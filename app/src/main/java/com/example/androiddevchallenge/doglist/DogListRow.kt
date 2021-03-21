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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.Dog
import com.example.androiddevchallenge.demoDogs
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun DogListRow(navController: NavController, dog: Dog) {
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
                .clickable(
                    onClick = {
                        navController.currentBackStackEntry?.arguments?.putParcelable("dog", dog)
                        navController.navigate(route = "dogDetailsScreen")
                    }
                )
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

@Preview("ListRow preview", showBackground = true)
@Composable
fun ListRowPreview() {
    MyTheme {
        DogListRow(navController = rememberNavController(), dog = demoDogs[0])
    }
}
