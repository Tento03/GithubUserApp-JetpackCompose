package com.example.githubappcompose.favorite

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private var dao: FavoriteDao) {
    suspend fun addFavorite(favorite: Favorite){
        return dao.addFavorite(favorite)
    }
    suspend fun getFavorite():Flow<List<Favorite>>{
        return dao.getFavorite()
    }
    suspend fun getFavoriteId(id:String):Flow<Favorite>{
        return dao.getFavoriteId(id)
    }
    suspend fun searchFavorite(query:String):Flow<List<Favorite>>{
        val searchQuery="%$query%"
        return dao.searchFavorite(searchQuery)
    }
    suspend fun deleteFavorite(id: String){
        return dao.deleteFavorite(id)
    }
}