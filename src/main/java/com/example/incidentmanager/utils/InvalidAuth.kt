package com.example.incidentmanager.utils


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InvalidAuth(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() }, // Dismiss al fer clic fora
        title = { Text(text = "Opps!") },
        text = { Text(text = "password or username incorrect") },
        confirmButton = {
            TextButton( onClick = { onDismiss() }) { // Dismiss al fer clic al ok
                Text("OK")
            }
        }
    )
}