package com.example.marvel_app.data.data_source.localDataSource


import com.example.marvel_app.domain.entities.CharactersCache
import javax.inject.Inject

interface LocalDataSource {

    suspend fun getCharacters(): List<CharactersCache>
    suspend fun insert(characters : CharactersCache)
    suspend fun delete(characters : CharactersCache)
}



class LocalDataSourceImplementer
@Inject
constructor(
    private val dao_characters: Dao_Characters

) : LocalDataSource {
    override suspend fun getCharacters():List<CharactersCache> {

        return dao_characters.getAllTask()
    }

    override suspend fun insert(characters: CharactersCache) {
        dao_characters.insertTask(characters)
    }

    override suspend fun delete(characters: CharactersCache) {
      dao_characters.deleteTask(characters)
    }


}