package com.example.hamdartestdemo.presentation.app_list

import com.example.hamdartestdemo.domain.model.App
import com.example.hamdartestdemo.domain.model.DataSource

data class AppListState(
    val isLoading: Boolean = false,
    val apps: List<App> = emptyList(),
    val dataSource: DataSource? = null,
    val error: String = "",
    val noData: Boolean = false
)
