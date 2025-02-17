package com.example.hamdartestdemo.presentation.app_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hamdartestdemo.common.Resource
import com.example.hamdartestdemo.domain.use_case.GetAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(AppListState())
    val state: State<AppListState> = _state

    init {
        getApps()
    }

    private fun getApps() {
        getAppsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AppListState(apps = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = AppListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = AppListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}