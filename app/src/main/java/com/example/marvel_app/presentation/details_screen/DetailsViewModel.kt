package com.example.marvel_app.presentation.details_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.core.BaseApplication
import com.example.marvel_app.core.NetworkResult
import com.example.marvel_app.data.responeses.CharactersApiResponse
import com.example.marvel_app.domain.usecase.CharactersDetails_UseCase
import com.example.marvel_app.domain.usecase.Characters_UseCase
import com.example.marvel_app.domain.usecase.InputData
import com.example.marvel_app.domain.usecase.InputDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel  @Inject constructor(private val charactersUsecase: CharactersDetails_UseCase): ViewModel() {
    val result = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())


     fun getCharacters_Details(id:String) {
         result.value = NetworkResult.Loading()
        viewModelScope.launch {
            charactersUsecase.execute(InputDetails(id)).fold(
                {
                    result.value = NetworkResult.Error(it.message)
                },{
                    result.value = NetworkResult.Success(it)
//                    result.value.data?.data?.results=list
                }
            )
        }
    }


}