package com.example.mediastreaming.view

data class MediaStream(
    val manifest: Manifest
)
data class Manifest(
    val url1: String,
    val url2: String,
    val url3: String
)