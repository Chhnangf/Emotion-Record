package org.example.easy_key

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform