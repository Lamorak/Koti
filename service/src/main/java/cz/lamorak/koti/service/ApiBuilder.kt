package cz.lamorak.koti.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiBuilder {

    private val loggingInterceptor by lazy { HttpLoggingInterceptor().setLevel(BODY) }

    fun catApi(): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(buildClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(CatApi::class.java)
    }

    private fun buildClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(::headerInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }
        return builder.build()
    }

    private fun headerInterceptor(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}