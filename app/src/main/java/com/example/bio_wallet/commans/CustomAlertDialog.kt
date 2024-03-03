package com.example.bio_wallet.commans

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CustomAlertDialog(alertText:String,dismiss:() -> Unit) {

        AlertDialog(
            onDismissRequest = {
                               dismiss()
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(alertText.toString())
            },
            confirmButton = {
                Button(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

