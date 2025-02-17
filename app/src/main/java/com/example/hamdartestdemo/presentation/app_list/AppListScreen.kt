package com.example.hamdartestdemo.presentation.app_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hamdartestdemo.presentation.Screen
import com.example.hamdartestdemo.presentation.app_list.components.AppListItem

@Composable
fun AppListScreen(
    navController: NavController,
    viewModel: AppListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.apps) { app ->
                AppListItem(
                    app = app,
                    onItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("item", app)
                        navController.navigate(Screen.AppDetailScreen.route)
                    }
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}