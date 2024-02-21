package com.example.marvel_app.data.network

import com.example.marvel_app.data.responeses.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    suspend fun getCharacters( @Query("apikey") apikey: String, @Query("hash") hash: String, @Query("ts") ts: String, @Query("limit") limit: String): CharactersApiResponse
    @GET("characters")
    suspend fun Characters_Details( @Query("apikey") apikey: String, @Query("hash") hash: String, @Query("ts") ts: String, @Query("id") id: String): CharactersApiResponse
}