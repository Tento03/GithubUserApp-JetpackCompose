package com.example.githubappcompose.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM githubCompose")
    fun getFavorite():Flow<List<Favorite>>

    @Query("SELECT * FROM githubCompose WHERE id=:id")
    fun getFavoriteId(id:String):Flow<Favorite>

    @Query("SELECT * FROM githubCompose WHERE login LIKE :query")
    fun searchFavorite(query:String):Flow<List<Favorite>>

    @Query("DELETE FROM githubCompose WHERE id=:id")
    fun deleteFavorite(id: String)
}
