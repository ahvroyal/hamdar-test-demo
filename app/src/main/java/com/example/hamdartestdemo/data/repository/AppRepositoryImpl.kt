package com.example.hamdartestdemo.data.repository

import com.example.hamdartestdemo.data.local.AppLocalDataSource
import com.example.hamdartestdemo.data.remote.AppApi
import com.example.hamdartestdemo.data.remote.dto.AppDto
import com.example.hamdartestdemo.domain.repository.AppRepository
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val appLocalDataSource: AppLocalDataSource
) : AppRepository {
    private val fileName = "app_cache.json"
    private val cacheValidity = 60 * 1000L

    override suspend fun getApps(): List<AppDto> {
        // Check if cached data is available and still valid
        if (appLocalDataSource.isCacheValid(fileName, cacheValidity)) {
            getCachedData()
        }

        // Fetch fresh data from network and update cache
        try {
            val appList = api.getApps()
            appLocalDataSource.saveDataToFile(fileName, appList)

            return appList
        } catch(e: HttpException) {
            return getCachedData()
        } catch(e: IOException) {
            return getCachedData()
        }
    }

    private suspend fun getCachedData(): List<AppDto> {
        val cachedData: List<AppDto>? = appLocalDataSource.readDataFromFile(fileName, object : TypeToken<List<AppDto>>() {})

        return cachedData ?: emptyList()
    }

}