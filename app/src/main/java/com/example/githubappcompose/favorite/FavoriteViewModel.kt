package com.example.githubappcompose.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubappcompose.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository):ViewModel() {
    private val _getFavorite=MutableStateFlow<List<Favorite>>(emptyList())
    val getFavorite:MutableStateFlow<List<Favorite>> = _getFavorite

    private val _getFavoriteId=MutableStateFlow<Favorite?>(null)
    val getFavoriteId:MutableStateFlow<Favorite?> = _getFavoriteId

    private val _searchFavorite=MutableStateFlow<List<Favorite>>(emptyList())
    val searchFavorite:MutableStateFlow<List<Favorite>> = _searchFavorite

    fun addFavorite(item: Item){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val favorite=Favorite(
                    avatar_url = item.avatar_url,
                    events_url = item.events_url,
                    followers_url = item.followers_url,
                    following_url = item.following_url,
                    gists_url = item.gists_url,
                    gravatar_id = item.gravatar_id,
                    html_url = item.html_url,
                    id = item.id,
                    login = item.login,
                    node_id = item.node_id,
                    organizations_url = item.organizations_url,
                    received_events_url = item.received_events_url,
                    repos_url = item.repos_url,
                    score = item.score,
                    site_admin = item.site_admin,
                    starred_url = item.starred_url,
                    subscriptions_url = item.subscriptions_url,
                    type = item.type,
                    url = item.url,
                    user_view_type = item.user_view_type
                )
                repository.addFavorite(favorite)
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getFavorite(){
        try {
            viewModelScope.launch {
                val response=repository.getFavorite().collect{
                    _getFavorite.value=it
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getFavoriteId(id:String){
        try {
            viewModelScope.launch {
                val response=repository.getFavoriteId(id).collect{
                    _getFavoriteId.value=it
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun searchFavorite(query:String){
        try {
            viewModelScope.launch {
                val response=repository.searchFavorite(query).collect{
                    _searchFavorite.value=it
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun deleteavorite(id:String){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response=repository.deleteFavorite(id)
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}