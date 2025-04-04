package com.example.hamdartestdemo.presentation.app_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hamdartestdemo.R
import com.example.hamdartestdemo.presentation.Screen
import com.example.hamdartestdemo.presentation.app_list.components.AppListItem
import com.example.hamdartestdemo.presentation.theme.*

@Composable
fun AppListScreen(
    navController: NavController,
    viewModel: AppListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.gray))
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Data source: ${state.dataSource?.name ?: "..."}",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(CenterVertically)
                        .weight(1f)
                )

                IconButton(
                    onClick = { navController.navigate(Screen.CloudSyncScreen.route) },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cloud,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    )
                }
                
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                IconButton(
                    onClick = { viewModel.getApps() },
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
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
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
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

            if (state.noData) {
                Text(
                    text = "No Data !",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}