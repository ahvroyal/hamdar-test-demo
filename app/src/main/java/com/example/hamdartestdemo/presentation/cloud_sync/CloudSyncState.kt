package com.example.hamdartestdemo.presentation.cloud_sync

data class CloudSyncState(
    val isLoading: Boolean = false,
    val objectsName: List<String>? = null,
    val isAppDataBackedUp: Boolean = false,
    val error: String = "",
)
