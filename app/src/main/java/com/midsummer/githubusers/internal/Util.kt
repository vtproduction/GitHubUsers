package com.midsummer.githubusers.internal

/**
 * Created by nienle on 03,September,2021
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */


fun randomImageUrl() : String {
    val width = (400..600).random()
    val height = (200..400).random()

    return "https://source.unsplash.com/random/${width}x${height}"
}