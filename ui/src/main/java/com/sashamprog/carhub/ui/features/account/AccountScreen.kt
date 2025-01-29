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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun AccountScreen(navController: NavController) {
    val viewModel: AccountViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    var nicknameEditable by remember { mutableStateOf(false) }
    var editableNickname by remember { mutableStateOf(user.nickname) }

    val pickAvatarLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let {
                val newAvatarUrl = uri.toString() // Convert to URL or upload to server
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar Section
        Box(
            modifier = Modifier
                .size(120.dp)
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
                        .size(100.dp)
                        .clickable { onEditAvatar() },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Nickname Section
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
                    editableNickname = user.nickname // Reset to original
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
            Text(
                text = user.nickname,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable { nicknameEditable = true }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Button(
            onClick = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo(0) // Clear back stack
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }
}