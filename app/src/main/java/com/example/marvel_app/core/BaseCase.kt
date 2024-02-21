package com.example.marvel_app.core

import arrow.core.Either
import com.example.marvel_app.data.network.Failure

interface BaseCase<In,Out> {
    suspend fun execute(input: In): Either<Failure, Out>

}