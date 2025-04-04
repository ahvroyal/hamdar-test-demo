package com.example.hamdartestdemo.presentation.cloud_sync

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hamdartestdemo.common.MinioHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val fileName = "app_cache.json"

@HiltViewModel
class CloudSyncViewModel @Inject constructor(
    private val minioHelper: MinioHelper
) : ViewModel() {

    private val TAG = CloudSyncViewModel::class.java.simpleName

    private val _state = mutableStateOf(CloudSyncState())
    val state: State<CloudSyncState> = _state

    init {
        fetchState()
    }

    fun fetchState() {
        _state.value = CloudSyncState(isLoading = true)

        viewModelScope.launch {
//            val bucketList = minioHelper.getBucketList()
            val objectList = minioHelper.getObjectList()

            if (objectList != null) {
                _state.value =
                    CloudSyncState(
                        objectsName = objectList,
                        isAppDataBackedUp = objectList.contains(fileName)
                    )
            } else {
                _state.value = CloudSyncState(error = "Unknown error !")
            }
        }
    }

    fun backupData() {
        _state.value = CloudSyncState(isLoading = true)

        viewModelScope.launch {
            val uploadFileResult = minioHelper.uploadFile()

            if (uploadFileResult != null) {
                fetchState()
            } else {
                _state.value = CloudSyncState(error = "Unknown error !")
            }
        }
    }

}