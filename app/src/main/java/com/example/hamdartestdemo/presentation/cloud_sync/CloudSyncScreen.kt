package com.example.hamdartestdemo.presentation.cloud_sync

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.CloudDone
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FilePresent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hamdartestdemo.R
import com.example.hamdartestdemo.presentation.theme.AppTheme

@Composable
fun CloudSyncScreen(
    viewModel: CloudSyncViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (state.objectsName != null) {
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
                        text = "Data Backup Status:",
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(CenterVertically)
                    )

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(50.dp)
                    ) {
                        if (state.isAppDataBackedUp) {
                            Icon(
                                imageVector = Icons.Outlined.CloudDone,
                                contentDescription = null,
                                tint = Color(0xFF43A047),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.CloudOff,
                                contentDescription = null,
                                tint = Color(0xFF757575),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                }

                Divider(color = Color.Transparent)

                if (state.objectsName.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(120.dp)
                            .background(
                                color = colorResource(id = R.color.gray),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = state.objectsName.joinToString(separator = "\n") { it },
                            style = MaterialTheme.typography.body2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(vertical = 12.dp, horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FilePresent,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        }
                    }
                }

                if (state.objectsName.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_bucket),
                        style = MaterialTheme.typography.caption,
                        color = Color(0xFF424242),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (state.isAppDataBackedUp) {
                    Text(
                        text = stringResource(R.string.backed_up),
                        style = MaterialTheme.typography.caption,
                        color = Color(0xFF424242),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = stringResource(R.string.not_backed_up),
                        style = MaterialTheme.typography.caption,
                        color = Color(0xFF424242),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (!state.isAppDataBackedUp) {
                ElevatedButton(
                    onClick = { viewModel.backupData() },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color(0xFF03A9F4)
                    ),
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Backup",
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White,
                        modifier = Modifier.align(CenterVertically)
                    )
                }
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
    }
}