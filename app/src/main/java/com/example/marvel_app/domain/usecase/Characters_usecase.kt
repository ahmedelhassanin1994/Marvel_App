package com.example.marvel_app.domain.usecase

import arrow.core.Either
import com.example.marvel_app.core.BaseCase
import com.example.marvel_app.data.network.Failure
import com.example.marvel_app.data.responeses.CharactersApiResponse
import com.example.marvel_app.domain.repository.Repository

class Characters_UseCase(private val repository : Repository) :
    BaseCase<InputData, CharactersApiResponse> {

    override suspend fun execute(input: InputData): Either<Failure, CharactersApiResponse> {
        return repository.getCharacters()
    }
}

 class  InputData(){

 }