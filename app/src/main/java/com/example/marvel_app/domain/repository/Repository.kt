package com.example.marvel_app.domain.repository

import arrow.core.Either
import com.example.marvel_app.data.network.Failure
import com.example.marvel_app.data.responeses.CharactersApiResponse

interface Repository {

    suspend fun getCharacters(): Either<Failure, CharactersApiResponse>
    suspend fun Characters_Details(id :String): Either<Failure, CharactersApiResponse>

}