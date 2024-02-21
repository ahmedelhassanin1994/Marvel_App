package com.example.marvel_app.core

import android.content.Context
import androidx.room.Room
import com.example.marvel_app.data.data_source.RemoteDataSource
import com.example.marvel_app.data.data_source.RemoteDataSourceImplementer
import com.example.marvel_app.data.data_source.localDataSource.AppDatabase
import com.example.marvel_app.data.data_source.localDataSource.Dao_Characters
import com.example.marvel_app.data.data_source.localDataSource.LocalDataSource
import com.example.marvel_app.data.data_source.localDataSource.LocalDataSourceImplementer
import com.example.marvel_app.data.network.MarvelApi
import com.example.marvel_app.data.repository.MovieRepositoryImpl
import com.example.marvel_app.domain.repository.Repository
import com.example.marvel_app.domain.usecase.CharactersDetails_UseCase
import com.example.marvel_app.domain.usecase.Characters_UseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): MarvelApi {
        return retrofitBuilder
            .build()
            .create(MarvelApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "characters_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase) : Dao_Characters {
        return appDatabase.CharactersDao()
    }
    @Singleton
    @Provides
    fun provideRemoteDataSource(api: MarvelApi
    ) = RemoteDataSourceImplementer(api) as RemoteDataSource

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: Dao_Characters
    ) = LocalDataSourceImplementer(dao) as LocalDataSource

    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ) = MovieRepositoryImpl(remoteDataSource,localDataSource) as Repository

    @Singleton
    @Provides
    fun provide_Usecase(
        repository: Repository
    ) = Characters_UseCase(repository)

    @Singleton
    @Provides
    fun provideDetails_Usecase(
        repository: Repository
    ) = CharactersDetails_UseCase(repository)
}