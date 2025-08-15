package com.godilabs.depensy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.godilabs.depensy.utils.PreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    var username by remember { mutableStateOf(preferencesManager.getUsername()) }
    var showUsernameDialog by remember { mutableStateOf(false) }

    if (showUsernameDialog) {
        UsernameEditDialog(
            currentUsername = username,
            onDismiss = { showUsernameDialog = false },
            onSave = { newUsername ->
                preferencesManager.setUsername(newUsername)
                username = preferencesManager.getUsername()
                showUsernameDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Paramètres",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(Modifier.height(16.dp)) }

            // Profile Section
            item {
                SettingsSection(title = "Profil") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Nom d'utilisateur",
                        subtitle = username,
                        onClick = { showUsernameDialog = true }
                    )
                    SettingsItem(
                        icon = Icons.Default.Email,
                        title = "Email",
                        subtitle = "user@example.com",
                        onClick = { /* TODO: Implement */ }
                    )
                }
            }

            // App Settings
            item {
                SettingsSection(title = "Application") {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Notifications",
                        subtitle = "Gérer les notifications",
                        onClick = { /* TODO: Implement */ }
                    )
                    SettingsItem(
                        icon = Icons.Default.Language,
                        title = "Langue",
                        subtitle = "Français",
                        onClick = { /* TODO: Implement */ }
                    )
                }
            }

            // Data & Privacy
            item {
                SettingsSection(title = "Données & Confidentialité") {
                    SettingsItem(
                        icon = Icons.Default.Backup,
                        title = "Sauvegarde",
                        subtitle = "Sauvegarder vos données",
                        onClick = { /* TODO: Implement */ }
                    )
                    SettingsItem(
                        icon = Icons.Default.FileDownload,
                        title = "Exporter les données",
                        subtitle = "Télécharger vos dépenses",
                        onClick = { /* TODO: Implement */ }
                    )
                    SettingsItem(
                        icon = Icons.Default.Security,
                        title = "Confidentialité",
                        subtitle = "Paramètres de confidentialité",
                        onClick = { /* TODO: Implement */ }
                    )
                }
            }

            // Support
            item {
                SettingsSection(title = "Support") {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "Aide & Support",
                        subtitle = "FAQ et contact",
                        onClick = { /* TODO: Implement */ }
                    )
                    SettingsItem(
                        icon = Icons.Default.Star,
                        title = "Évaluer l'app",
                        subtitle = "Laisser un avis",
                        onClick = { /* TODO: Implement */ }
                    )
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "À propos",
                        subtitle = "Version 1.0.0",
                        onClick = { /* TODO: Implement */ }
                    )
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun UsernameEditDialog(
    currentUsername: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(currentUsername) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Modifier le nom d'utilisateur",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    label = { Text("Nom d'utilisateur") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (textFieldValue.trim().isNotBlank()) {
                                onSave(textFieldValue)
                            }
                        }
                    ),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Black.copy(
                                alpha = 0.6f
                            )
                        )
                    ) {
                        Text("Annuler")
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (textFieldValue.trim().isNotBlank()) {
                                onSave(textFieldValue)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Enregistrer")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    Color.Black.copy(alpha = 0.08f),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }
}