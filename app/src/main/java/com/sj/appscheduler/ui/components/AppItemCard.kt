package com.sj.appscheduler.ui.components // AppItemCard.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sj.appscheduler.R
import com.sj.appscheduler.models.AppInfoUiModel
import com.sj.appscheduler.ui.theme.AppSchedulerTheme

@Composable
fun AppItemCard(
    appInfo: AppInfoUiModel,
    onScheduleClick: () -> Unit
) {
    val context = LocalContext.current
    context.getDrawable(R.drawable.ic_launcher_foreground)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = appInfo.icon),
                contentDescription = "${appInfo.name} icon",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = appInfo.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = appInfo.packageName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = onScheduleClick) {
                Text("Schedule")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppItemCardPreview() {
    val context = LocalContext.current
    val drawable = context.getDrawable(R.drawable.ic_launcher_foreground)!!

    AppSchedulerTheme {
        AppItemCard(
            appInfo = AppInfoUiModel(
                name = "Youtube",
                packageName = "com.android.youtube",
                icon = drawable
            ),
            onScheduleClick = {}
        )
    }
}
