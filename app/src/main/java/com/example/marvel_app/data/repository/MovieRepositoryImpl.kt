package com.example.marvel_app.data.repository

import android.util.Log
import arrow.core.Either
import com.example.marvel_app.core.BaseApplication
import com.example.marvel_app.core.Network
import com.example.marvel_app.data.data_source.RemoteDataSource
import com.example.marvel_app.data.data_source.localDataSource.LocalDataSource
import com.example.marvel_app.data.network.ApiInternalStatus
import com.example.marvel_app.data.network.Failure
import com.example.marvel_app.data.network.ResponseCode
import com.example.marvel_app.data.network.ResponseMessage
import com.example.marvel_app.data.responeses.CharactersApiResponse
import com.example.marvel_app.domain.entities.CharactersCache
import com.example.marvel_app.domain.repository.Repository
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
class MovieRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): Repository {


    override suspend fun getCharacters(): Either<Failure, CharactersApiResponse> {

        if (Network.checkConnectivity(BaseApplication.applicationContext())) {

            try {
                var response=remoteDataSource.getCharacters()

                if (response.code==ApiInternalStatus.Sucess){
                    val gson = Gson()
                    val json = gson.toJson(response)

                  Log.d("MovieRepositoryImpl", "${json} ")

               localDataSource.insert(CharactersCache(key = "Characters", date = "${json}"))
                    return Either.Right(response)
                }else{

                    return Either.Left(Failure(ResponseCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST))
                }
            }catch (e:Exception){

                return Either.Left(Failure(ResponseCode.CONNECT_TIMEOUT, e.message.toString()))

            }
        }else{

            try {
                val data_characters=localDataSource.getCharacters()
                Log.d("MovieRepositoryImpl", "${data_characters.last().date} ")


                var gson = Gson()
                var charactersApiResponse = gson.fromJson(data_characters.last().date, CharactersApiResponse::class.java)

                Log.d("MovieRepositoryImpl", "getCharacters: ${charactersApiResponse.data?.count} ")

                return Either.Right(charactersApiResponse)

            }catch (e:Exception){
                return Either.Left(Failure(ResponseCode.NI_INTERNET_CONNECTION,  ResponseMessage.BAD_REQUEST))
            }



        }
    }

    override suspend fun Characters_Details(id: String): Either<Failure, CharactersApiResponse> {

        if (Network.checkConnectivity(BaseApplication.applicationContext())) {

            try {
                var response=remoteDataSource.Characters_Details(id)

                if (response.code==ApiInternalStatus.Sucess){
                    return Either.Right(response)
                }else{

                    return Either.Left(Failure(ResponseCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST))
                }
            }catch (e:Exception){

                return Either.Left(Failure(ResponseCode.CONNECT_TIMEOUT, e.message.toString()))

            }
        }else{
            return Either.Left(Failure(ResponseCode.NI_INTERNET_CONNECTION,  ResponseMessage.BAD_REQUEST))
        }
    }


}