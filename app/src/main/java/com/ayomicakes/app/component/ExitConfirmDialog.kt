package com.ayomicakes.app.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.ayomicakes.app.utils.BackNavElement

@Composable
fun ExitDialog(onNavIconClicked: () -> Unit): BackNavElement {
    var showDialog by remember { mutableStateOf(false) }

    val defaultBackHandler = BackNavElement.needsProcessing {
        if (!showDialog) {
            showDialog = true
            BackNavElement.Result.CANNOT_GO_BACK
        } else {
            BackNavElement.Result.CAN_GO_BACK
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { 
                    Text(text = "Confirm the Exit")
            },
            text = {
                Text(text = "Are you sure want to Exit?") },
            confirmButton = {
                Button(onClick = {
                    onNavIconClicked()
                }) {
                    Text("Back")
                }
            },
            dismissButton = {}
        )
    }

    return defaultBackHandler
}
