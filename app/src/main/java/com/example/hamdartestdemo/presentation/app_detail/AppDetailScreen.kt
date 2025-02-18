package com.example.hamdartestdemo.presentation.app_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hamdartestdemo.common.Constants
import com.example.hamdartestdemo.domain.model.App
import com.example.hamdartestdemo.presentation.app_list.components.shimmerBrush
import com.example.hamdartestdemo.presentation.theme.AppTheme

@Composable
fun AppDetailScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val app = navController.previousBackStackEntry?.savedStateHandle?.get<App>("item")

        if (app != null) {
            var isImageLoading by remember { mutableStateOf(true) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = app.appName,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(CenterVertically)
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.ICON_URL.plus(app.iconUrl))
                            .crossfade(true)
                            .build(),
                        onSuccess = { isImageLoading = false },
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = shimmerBrush(
                                    targetValue = 1300f,
                                    showShimmer = isImageLoading
                                )
                            )
                            .align(CenterVertically)
                    )
                }

                Divider(
                    color = Color.Transparent,
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = app.toString(),
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppListItemPreview() {
    AppTheme {
        AppDetailScreen(navController = rememberNavController())
    }
}