package com.example.evaluation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform