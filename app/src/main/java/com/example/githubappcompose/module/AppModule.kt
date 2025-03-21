package com.example.githubappcompose.module

import android.content.Context
import androidx.room.Room
import com.example.githubappcompose.api.GithubApi
import com.example.githubappcompose.favorite.FavoriteDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesRetrofit():Retrofit=Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesGithubApi(retrofit: Retrofit)=retrofit.create(GithubApi::class.java)

    @Singleton
    @Provides
    fun providesFavoriteDB(@ApplicationContext app: Context):FavoriteDB=Room.databaseBuilder(
        app.applicationContext,FavoriteDB::class.java,"GithubUserCompose"
    ).build()

    @Singleton
    @Provides
    fun providesFavoriteDao(db: FavoriteDB)=db.favoriteDao()
}