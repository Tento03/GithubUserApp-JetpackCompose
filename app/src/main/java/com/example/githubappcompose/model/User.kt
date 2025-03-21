package com.example.githubappcompose.model

data class User(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)