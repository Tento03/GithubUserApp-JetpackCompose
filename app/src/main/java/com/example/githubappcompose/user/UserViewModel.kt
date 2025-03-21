package com.example.githubappcompose.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.githubappcompose.model.Item
import com.example.githubappcompose.model.ItemX
import com.example.githubappcompose.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private var repository: UserRepository):ViewModel() {
    private val _searchRepo=MutableStateFlow<List<ItemX>>(emptyList())
    val searchRepo:MutableStateFlow<List<ItemX>> =_searchRepo

    private val _searchUser=MutableStateFlow<List<Item>>(emptyList())
    val searchUser:MutableStateFlow<List<Item>> =_searchUser

    private val _detailUser=MutableStateFlow<Item?>(null)
    val detailUser:MutableStateFlow<Item?> = _detailUser

    private val _getFollowers=MutableStateFlow<List<Item>>(emptyList())
    val getFollowers:MutableStateFlow<List<Item>> = _getFollowers

    private val _getFollowings=MutableStateFlow<List<Item>>(emptyList())
    val getFollowings:MutableStateFlow<List<Item>> = _getFollowings

    fun searchRepo(q:String){
        try {
            viewModelScope.launch {
                val response=repository.searchRepo(q).items
                _searchRepo.value=response
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun searchUser(q:String){
        try {
            viewModelScope.launch {
                val response=repository.searchUser(q).items
                _searchUser.value=response
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun detailUser(username:String){
        try {
            viewModelScope.launch {
                val response=repository.detailUser(username)
                _detailUser.value=response
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getFollowers(username: String){
        try {
            viewModelScope.launch {
                val repository=repository.getFollowers(username)
                _getFollowers.value=repository
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getFollowings(username: String){
        try {
            viewModelScope.launch {
                val response=repository.getFollowings(username)
                _getFollowings.value=response
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}