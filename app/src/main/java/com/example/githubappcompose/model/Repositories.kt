package com.example.githubappcompose.model

data class Repositories(
    val incomplete_results: Boolean,
    val items: List<ItemX>,
    val total_count: Int
)