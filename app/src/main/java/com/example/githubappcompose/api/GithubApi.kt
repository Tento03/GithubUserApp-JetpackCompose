package com.example.githubappcompose.api

import com.example.githubappcompose.model.Item
import com.example.githubappcompose.model.Repositories
import com.example.githubappcompose.model.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi{

    @GET("search/repositories")
    suspend fun searchRepo(
        @Query("q")q:String
    ):Repositories

    @GET("search/users")
    suspend fun searchUser(
        @Query("q")q:String
    ):User

    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username")username:String
    ):Item

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username")username: String
    ):List<Item>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username")username: String
    ):List<Item>

}