package com.example.hamdartestdemo.domain.use_case

import com.example.hamdartestdemo.common.Resource
import com.example.hamdartestdemo.data.remote.dto.toApp
import com.example.hamdartestdemo.domain.model.App
import com.example.hamdartestdemo.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<Resource<List<App>>> = flow {
        try {
            emit(Resource.Loading<List<App>>())
            val apps = repository.getApps().map { it.toApp() }
            emit(Resource.Success<List<App>>(apps))
        } catch(e: HttpException) {
            emit(Resource.Error<List<App>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<App>>("Couldn't reach server. Check your internet connection."))
        }
    }
}