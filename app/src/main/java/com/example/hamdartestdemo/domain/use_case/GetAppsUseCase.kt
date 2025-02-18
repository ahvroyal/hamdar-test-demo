package com.example.hamdartestdemo.domain.use_case

import com.example.hamdartestdemo.common.Resource
import com.example.hamdartestdemo.data.remote.dto.AppDto
import com.example.hamdartestdemo.data.remote.dto.toApp
import com.example.hamdartestdemo.domain.model.App
import com.example.hamdartestdemo.domain.model.DataSource
import com.example.hamdartestdemo.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<Resource<Pair<List<App>, DataSource>>> = flow {
        try {
            emit(Resource.Loading())

            val data = repository.getApps()
            val apps = data.first.map { it.toApp() }
            val dataSource = data.second

            emit(Resource.Success(Pair(apps, dataSource)))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}