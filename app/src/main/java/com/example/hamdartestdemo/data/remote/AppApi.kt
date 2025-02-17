package com.example.hamdartestdemo.data.remote

import com.example.hamdartestdemo.data.remote.dto.AppDto
import retrofit2.http.GET

interface AppApi {

    @GET("defaultAppList")
    suspend fun getApps(): List<AppDto>

}