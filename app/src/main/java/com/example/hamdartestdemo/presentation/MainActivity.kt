package com.example.hamdartestdemo.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.amazonaws.auth.BasicAWSCredentials
//import com.amazonaws.regions.Regions
//import com.amazonaws.services.s3.AmazonS3Client
//import com.amazonaws.services.s3.model.PutObjectRequest
//import com.amazonaws.regions.Region
import com.example.hamdartestdemo.presentation.app_detail.AppDetailScreen
import com.example.hamdartestdemo.presentation.cloud_sync.CloudSyncScreen
import com.example.hamdartestdemo.presentation.app_list.AppListScreen
import com.example.hamdartestdemo.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.minio.ListObjectsArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import io.minio.errors.MinioException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
//import software.amazon.awssdk.services.s3.S3Client
//import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import java.io.File
import javax.inject.Inject
import javax.xml.stream.XMLInputFactory

const val accessKey = "Q3AM3UQ867SPQQA43P2F"
const val secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG"
const val minioEndpoint = "https://play.min.io"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var minioClient: MinioClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 13)
            }
        }

        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val coroutineScope = rememberCoroutineScope()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.AppListScreen.route
                    ) {
                        composable(
                            route = Screen.AppListScreen.route
                        ) {
                            AppListScreen(navController)
                        }

                        composable(
                            route = Screen.AppDetailScreen.route
                        ) {
                            AppDetailScreen(navController)
                        }

                        composable(
                            route = Screen.CloudSyncScreen.route
                        ) {
                            CloudSyncScreen()

//                            CloudSyncScreen(
//                                onBackupClicked = {
//                                    coroutineScope.launch {
//                                        val result = uploadFileToMinioServer()
//                                        Toast.makeText(
//                                            this@MainActivity,
//                                            result,
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                }
//                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun uploadFileToMinioServer(): String {
        val fileName = "app_cache.json"
        val bucketName = "some-random-test-bucket"
        var result: String

        withContext(Dispatchers.IO) {
            val file = File(cacheDir, fileName)
            if (!file.exists()) {
                result = "File doesn't exist !"
                return@withContext
            }

            // Minio Java SDK operations
            try {
                val bucketList = minioClient.listBuckets()
                val objectList = minioClient.listObjects(
                    ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build()
                )
                result = "Information retrieved !"

                Log.v("MainActivity", bucketList.joinToString { it.name() })
                Log.v("MainActivity", objectList.joinToString { it.get().objectName() })
            } catch (e: Exception) {
                result = e.message.toString()
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
                result = "Upload is successful !"
            } catch (e: MinioException) {
                result = e.message.toString()
            }
        }

        return result
    }

//    private suspend fun uploadFileToMinioServer2(): String {
//        val credentials = BasicAWSCredentials(accessKey, secretKey)
//        val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.DEFAULT_REGION))
//        s3Client.setEndpoint(minioEndpoint)
//
//        val fileName = "app_cache.json"
//        val bucketName = "some-random-test-bucket"
//        var result: String
//
//        withContext(Dispatchers.IO) {
//            val file = File(cacheDir, fileName)
//            if (!file.exists()) {
//                result = "File doesn't exist !"
//                return@withContext
//            }
//
//            // AWS S3 Android SDK operations
//            try {
//                val bucketList = s3Client.listBuckets()
//                val objectList = s3Client.listObjects(bucketName).objectSummaries
//                result = "Information retrieved !"
//
//                Log.v("MainActivity", bucketList.joinToString { it.name })
//                Log.v("MainActivity", objectList.joinToString { it.key })
//            } catch (e: Exception) {
//                result = e.message.toString()
//            }
//
//            // Upload a file
//            try {
//                val putObjectResult = s3Client.putObject(
//                    bucketName,
//                    fileName,
//                    file
//                )
//                result = "Upload is successful !"
//
//                Log.v("MainActivity", putObjectResult.toString())
//            } catch (e: Exception) {
//                result = e.message.toString()
//            }
//
//            // Minio Java Client API
////            try {
////                // Upload json file
////                minioClient.uploadObject(
////                    UploadObjectArgs.Builder()
////                        .bucket(bucketName)
////                        .`object`(fileName)
////                        .filename(file.path)
////                        .build()
////                )
////                // Upload is successful, if no exception is caught
////                result = "Upload is successful !"
////            } catch (e: MinioException) {
////                result = e.message.toString()
////            }
//        }
//
//        return result
//    }

//    private suspend fun uploadFileToMinioServer3(): String {
//        val s3Client: S3Client = S3Client.builder()
//            .region(software.amazon.awssdk.regions.Region.AWS_GLOBAL)
//            .credentialsProvider(StaticCredentialsProvider.create(
//                AwsBasicCredentials.create(accessKey, secretKey)
//            ))
//            .endpointOverride(java.net.URI.create(minioEndpoint))
//            .build()
//
//        val fileName = "app_cache.json"
//        val bucketName = "some-random-test-bucket"
//        var result: String
//
//        withContext(Dispatchers.IO) {
//            val file = File(cacheDir, fileName)
//            if (!file.exists()) {
//                result = "File doesn't exist !"
//                return@withContext
//            }
//
//            // Upload a file
//            try {
//                val request = software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .build()
//                val requestResult = s3Client.putObject(request, file.toPath())
//
//                result = "Upload is successful !"
//
//                Log.v("MainActivity", requestResult.toString())
//            } catch (e: Exception) {
//                result = e.message.toString()
//            }
//        }
//
//        return result
//    }

}