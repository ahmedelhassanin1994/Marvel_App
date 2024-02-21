package com.example.marvel_app.data.data_source

import android.util.Log
import com.example.marvel_app.core.Constant
import com.example.marvel_app.core.getHash
import com.example.marvel_app.data.network.MarvelApi
import com.example.marvel_app.data.responeses.CharactersApiResponse
import javax.inject.Inject

interface RemoteDataSource {

    suspend fun getCharacters(): CharactersApiResponse
    suspend fun Characters_Details(id :String): CharactersApiResponse

}



class RemoteDataSourceImplementer
@Inject
constructor(
    private val api: MarvelApi

) : RemoteDataSource {
    override suspend fun getCharacters(): CharactersApiResponse {
        val ts = System.currentTimeMillis().toString()
        val apiSecret = Constant.privateKey
        val apiKey = Constant.publicKey
        val hash = getHash(ts, apiSecret, apiKey)
       val data=api.getCharacters(apiKey,hash,ts,"20")
       return  data
    }

    override suspend fun Characters_Details(id: String): CharactersApiResponse {
        val ts = System.currentTimeMillis().toString()
        val apiSecret = Constant.privateKey
        val apiKey = Constant.publicKey
        val hash = getHash(ts, apiSecret, apiKey)
        val data=api.Characters_Details(apiKey,hash,ts,"$id")
        return  data;
    }

}