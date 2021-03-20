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

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class Sex {
    Male, Female
}

@Parcelize
data class Dog(
    val id: String,
    val imageUrl: String = "",
    val name: String,
    val breed: String? = null,
    val sex: Sex
) : Parcelable

data class SizeResult(
    val imperial: String,
    val metric: String
)

data class DogBreedResult(
    val id: Int,
    val name: String,
    val weight: SizeResult?,
    val height: SizeResult?,
    val life_span: String?,
    val breed_group: String?
)

data class DogResult(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<DogBreedResult>?
)

val demoDogs: List<Dog> = listOf(
    Dog(
        "0",
        "https://cdn2.thedogapi.com/images/dL8IihY5U.jpg",
        "Spaniard",
        "Cocker Spaniel",
        Sex.Male
    ),
    Dog(
        "1",
        "https://cdn2.thedogapi.com/images/SkJ3blcN7_1280.jpg",
        "Suzy",
        "Dalmatian",
        Sex.Female
    ),
    Dog(
        "2",
        "https://cdn2.thedogapi.com/images/HJWZZxc4X_1280.jpg",
        "Brittany",
        "Brittany",
        Sex.Female
    ),
    Dog(
        "3",
        "https://cdn2.thedogapi.com/images/GhtSdrW29.jpg",
        "Allie",
        "Alaskan Malamute",
        Sex.Female
    ),
    Dog(
        "4",
        "https://cdn2.thedogapi.com/images/Bkdx2g5Em_1280.jpg",
        "SnowWhite",
        "West Highland White Terrier",
        Sex.Male
    ),
    Dog(
        "5",
        "https://cdn2.thedogapi.com/images/nwpd32sdl.jpg",
        "Shiva",
        "Shiba Inu",
        Sex.Female
    ),
    Dog(
        "6",
        "https://cdn2.thedogapi.com/images/YnI54_KH9.jpg",
        "Bernard",
        "Saint Bernard",
        Sex.Male
    ),
    Dog(
        "7",
        "https://cdn2.thedogapi.com/images/By-hGecVX_1280.jpg",
        "Guinness",
        "Irish Terrier",
        Sex.Male
    ),
    Dog(
        "8",
        "https://cdn2.thedogapi.com/images/8jjWfmjNt.jpg",
        "Fluffy",
        "American Eskimo Dog (Miniature)",
        Sex.Male
    ),
    Dog(
        "9",
        "https://cdn2.thedogapi.com/images/xAo4Tfbyg.jpg",
        "Albert",
        "Australian Cattle Dog",
        Sex.Male
    )
)
