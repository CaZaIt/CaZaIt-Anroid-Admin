package org.cazait.network.di

import org.cazait.network.NetworkConnectivity
import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.cazait.network.Network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }

    @Provides
    @Singleton
    fun providesHttpLogger(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun providesHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        logger: HttpLoggingInterceptor,
        headerInterceptor: Interceptor,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(headerInterceptor)
            addInterceptor(logger)
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        }
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}