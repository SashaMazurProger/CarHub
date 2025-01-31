package com.sashamprog.carhub.ui.features.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreen(appController: NavController, onSignedOut: () -> Unit) {
    val viewModel: AccountViewModel = koinViewModel()
    val user by viewModel.user.collectAsState()
    var nicknameEditable by remember { mutableStateOf(false) }
    var editableNickname by remember { mutableStateOf(user.nickname) }

    val pickAvatarLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let {
                val newAvatarUrl = uri.toString()
                viewModel.updateAvatar(newAvatarUrl)
            }
        }
    )

    fun onEditAvatar() {
        pickAvatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
            if (user.avatarUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(user.avatarUrl),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onEditAvatar() }
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Default Avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onEditAvatar() },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Avatar",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .clickable { onEditAvatar() },
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (nicknameEditable) {
            OutlinedTextField(
                value = editableNickname,
                onValueChange = { editableNickname = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nickname") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    nicknameEditable = false
                    editableNickname = user.nickname
                }) {
                    Text("Cancel")
                }
                Button(onClick = {
                    nicknameEditable = false
                    viewModel.updateNickname(editableNickname)
                }) {
                    Text("Save")
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { nicknameEditable = true }
            ) {
                Text(
                    text = user.nickname,
                    style = MaterialTheme.typography.titleLarge
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Nickname",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.logout()
                onSignedOut()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }
}