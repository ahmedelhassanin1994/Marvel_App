package com.example.marvel_app.data.network


import com.example.marvel_app.core.Constant
import com.example.marvel_app.core.getHash
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService{

    private fun getRetrofit(): Retrofit {

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .build()
            request = request.newBuilder().url(url)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(clientInterceptor)


            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: MarvelApi = getRetrofit().create(MarvelApi::class.java)
}
