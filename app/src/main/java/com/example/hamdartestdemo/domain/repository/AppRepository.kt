package com.example.hamdartestdemo.domain.repository

import com.example.hamdartestdemo.data.remote.dto.AppDto
import com.example.hamdartestdemo.domain.model.DataSource

interface AppRepository {

    suspend fun getApps(): Pair<List<AppDto>, DataSource>

}