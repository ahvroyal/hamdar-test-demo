package com.example.hamdartestdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hamdartestdemo.presentation.app_detail.AppDetailScreen
import com.example.hamdartestdemo.presentation.app_list.AppListScreen
import com.example.hamdartestdemo.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.AppListScreen.route
                    ) {
                        composable(
                            route = Screen.AppListScreen.route
                        ) {
                            AppListScreen(navController)
                        }

                        composable(
                            route = Screen.AppDetailScreen.route
                        ) {
                            AppDetailScreen(navController)
                        }
                    }
                }
            }
        }
    }

}