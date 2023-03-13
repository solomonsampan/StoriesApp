package com.example.storiesapp.database

data class User(
    val id: Int = -1,
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)
