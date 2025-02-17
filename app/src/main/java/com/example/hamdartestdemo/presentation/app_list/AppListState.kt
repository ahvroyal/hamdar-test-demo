package com.example.hamdartestdemo.presentation.app_list

import com.example.hamdartestdemo.domain.model.App

data class AppListState(
    val isLoading: Boolean = false,
    val apps: List<App> = emptyList(),
    val error: String = ""
)
