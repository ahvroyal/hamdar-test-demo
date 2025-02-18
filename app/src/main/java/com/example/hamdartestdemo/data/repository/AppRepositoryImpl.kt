package com.example.hamdartestdemo.data.repository

import com.example.hamdartestdemo.data.local.AppLocalDataSource
import com.example.hamdartestdemo.data.remote.AppApi
import com.example.hamdartestdemo.data.remote.dto.AppDto
import com.example.hamdartestdemo.domain.model.DataSource
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

    override suspend fun getApps(): Pair<List<AppDto>, DataSource> {
        // Check if cached data is available and still valid
        if (appLocalDataSource.isCacheValid(fileName, cacheValidity)) {
            return Pair(getCachedData(), DataSource.LOCAL_STORAGE)
        } else {
            // Fetch fresh data from network and update cache
            try {
                val appList = api.getApps()
                appLocalDataSource.saveDataToFile(fileName, appList)

                return Pair(appList, DataSource.NETWORK)
            } catch(e: HttpException) {
                return Pair(getCachedData(), DataSource.LOCAL_STORAGE)
            } catch(e: IOException) {
                return Pair(getCachedData(), DataSource.LOCAL_STORAGE)
            }
        }
    }

    private suspend fun getCachedData(): List<AppDto> {
        val cachedData: List<AppDto>? = appLocalDataSource.readDataFromFile(fileName, object : TypeToken<List<AppDto>>() {})

        return cachedData ?: emptyList()
    }

}