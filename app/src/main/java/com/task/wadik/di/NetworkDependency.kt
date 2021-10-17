package com.task.wadik.di

import com.task.wadik.BuildConfig
import com.task.wadik.data.network.Urls
import com.task.wadik.data.network.WebServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Network dependency module.
 * Provides Retrofit dependency with OkHttp logger.
 */
val NetworkDependency = module {
    single {
        val builder = OkHttpClient().newBuilder()
        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.writeTimeout(15, TimeUnit.SECONDS)
        if (false) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)
        }
        val client = builder.build()
        Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    factory { get<Retrofit>().create(WebServices::class.java) }
}