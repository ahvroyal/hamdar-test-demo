package com.example.hamdartestdemo.common

import android.content.Context
import android.util.Log
import io.minio.ListObjectsArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import io.minio.errors.MinioException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

private const val fileName = "app_cache.json"
private const val bucketName = "some-random-test-bucket"

class MinioHelper (
    private val context: Context,
    private val minioClient: MinioClient
) {
    private val TAG = MinioHelper::class.java.simpleName

    suspend fun getBucketList(): List<String>? {
        return withContext(Dispatchers.IO) {
            try {
                val bucketList = minioClient.listBuckets().map { it.name() }
                Log.i(TAG, "Bucket list: ${bucketList.joinToString { it }}")

                return@withContext bucketList
            } catch (e: Exception) {
                Log.i(TAG, e.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getObjectList(): List<String>? {
        return withContext(Dispatchers.IO) {
            try {
                val objectList = minioClient.listObjects(
                    ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build()
                )
                val objectsName = objectList.toList().map { it.get().objectName().toString() }
                Log.i(TAG, "Object list: ${objectsName.joinToString { it }}")

                return@withContext objectsName
            } catch (e: Exception) {
                Log.i(TAG, e.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun uploadFile(): String? {
        return withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, fileName)
            if (!file.exists()) {
                Log.i(TAG, "File doesn't exist !")
                return@withContext null
            }

            // Upload json file
            try {
                minioClient.uploadObject(
                    UploadObjectArgs.Builder()
                        .bucket(bucketName)
                        .`object`(fileName)
                        .filename(file.toPath().toString())
                        .build()
                )
                // Upload is successful, if no exception is caught
                return@withContext "Upload is successful !"
            } catch (e: MinioException) {
                Log.i(TAG, e.message.toString())
                return@withContext null
            }
        }
    }
}