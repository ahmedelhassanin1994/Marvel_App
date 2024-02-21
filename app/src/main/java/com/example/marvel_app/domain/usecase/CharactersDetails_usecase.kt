package com.example.marvel_app.domain.usecase

import arrow.core.Either
import com.example.marvel_app.core.BaseCase
import com.example.marvel_app.data.network.Failure
import com.example.marvel_app.data.responeses.CharactersApiResponse
import com.example.marvel_app.domain.repository.Repository

class CharactersDetails_UseCase(private val repository : Repository) :
    BaseCase<InputDetails, CharactersApiResponse> {

    override suspend fun execute(input: InputDetails): Either<Failure, CharactersApiResponse> {
        return repository.Characters_Details(input.id)
    }
}

 data class  InputDetails(val id :String,)