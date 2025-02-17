package com.example.hamdartestdemo.domain.repository

import com.example.hamdartestdemo.data.remote.dto.AppDto

interface AppRepository {

    suspend fun getApps(): List<AppDto>

}