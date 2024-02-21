package com.example.marvel_app.presentation.home

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
import com.example.marvel_app.data.responeses.Results
import com.example.marvel_app.domain.usecase.Characters_UseCase
import com.example.marvel_app.domain.usecase.InputData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel  @Inject constructor(private val charactersUsecase: Characters_UseCase): ViewModel() {
    val result = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())
    var list_search: List<Results> = listOf()

    init {

        getCharacters()
    }
     fun getCharacters() {
         result.value = NetworkResult.Loading()
        viewModelScope.launch {
            charactersUsecase.execute(InputData()).fold(
                {
                    result.value = NetworkResult.Error(it.message)
                },{
                    result.value = NetworkResult.Success(it)
//                    result.value.data?.data?.results=list
                }
            )
        }
    }

    fun addSearchedFOrItemsToSearchedList(search:String){
        list_search= result.value.data?.data?.results!!.filter {
            it.name!!.startsWith(search)
        }
    }
}