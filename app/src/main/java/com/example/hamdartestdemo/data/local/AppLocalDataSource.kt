package com.example.hamdartestdemo.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AppLocalDataSource (
    private val context: Context
) {
    suspend fun saveDataToFile(fileName: String, data: Any) {
        withContext(Dispatchers.IO) {
            val json = Gson().toJson(data)
            val file = File(context.cacheDir, fileName)
            file.writeText(json)
        }
    }

    suspend fun <T> readDataFromFile(fileName: String, type: TypeToken<T>): T? {
        return withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, fileName)
            if (!file.exists()) return@withContext null

            val json = file.readText()
            return@withContext Gson().fromJson(json, type.type)
        }
    }

    suspend fun isCacheValid(fileName: String, maxAgeMillis: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, fileName)
            if (!file.exists()) return@withContext false

            val lastModified = file.lastModified()
            val currentTime = System.currentTimeMillis()

            return@withContext (currentTime - lastModified) < maxAgeMillis
        }
    }
}