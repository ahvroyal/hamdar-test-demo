package com.example.hamdartestdemo.presentation

sealed class Screen(val route: String) {
    object AppListScreen: Screen("app_list_screen")
    object AppDetailScreen: Screen("app_detail_screen")
    object CloudSyncScreen: Screen("cloud_sync_screen")
}