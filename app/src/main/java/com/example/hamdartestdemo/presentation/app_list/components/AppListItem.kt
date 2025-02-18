package com.example.hamdartestdemo.presentation.app_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hamdartestdemo.common.Constants
import com.example.hamdartestdemo.domain.model.App
import com.example.hamdartestdemo.presentation.theme.AppTheme
import com.example.hamdartestdemo.presentation.theme.primaryContainerLight

@Composable
fun AppListItem(
    app: App,
    onItemClick: (App) -> Unit
) {
    var isImageLoading by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(app) }
            .padding(20.dp),
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
}

@Preview(showBackground = true)
@Composable
fun AppListItemPreview() {
    AppTheme {
        AppListItem(
            app = App(
                appName = "Chrome",
                cat = "",
                createdAt = "",
                iconUrl = "assets/images/icons/com.android.chrome.png",
                id = "",
                pkgName = "",
                updatedAt = ""
            ),
            onItemClick = {}
        )
    }
}