package com.example.githubappcompose.user

import com.example.githubappcompose.api.GithubApi
import com.example.githubappcompose.model.Item
import com.example.githubappcompose.model.Repositories
import com.example.githubappcompose.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private var githubApi: GithubApi) {
    suspend fun searchRepo(q:String):Repositories{
        return githubApi.searchRepo(q)
    }
    suspend fun searchUser(q:String):User{
        return githubApi.searchUser(q)
    }
    suspend fun detailUser(username:String):Item{
        return githubApi.detailUser(username)
    }
    suspend fun getFollowers(username: String):List<Item>{
        return githubApi.getFollowers(username)
    }
    suspend fun getFollowings(username: String):List<Item>{
        return githubApi.getFollowing(username)
    }
}