package com.example.hamdartestdemo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.hamdartestdemo.common.Constants
import com.example.hamdartestdemo.common.MinioHelper
import com.example.hamdartestdemo.data.local.AppLocalDataSource
import com.example.hamdartestdemo.data.remote.AppApi
import com.example.hamdartestdemo.data.repository.AppRepositoryImpl
import com.example.hamdartestdemo.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.minio.MinioClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideAppApi(
        client: OkHttpClient,
    ): AppApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileUtil(@ApplicationContext context: Context): AppLocalDataSource {
        return AppLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        api: AppApi,
        appLocalDataSource: AppLocalDataSource
    ): AppRepository {
        return AppRepositoryImpl(api, appLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideMinioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint("https://play.min.io")
            .credentials(
                "Q3AM3UQ867SPQQA43P2F",
                "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMinioHelper(
        @ApplicationContext context: Context,
        minioClient: MinioClient
    ): MinioHelper {
        return MinioHelper(
            context,
            minioClient
        )
    }

}