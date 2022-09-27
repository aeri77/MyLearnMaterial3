package com.ayomicakes.app.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ayomicakes.app.ui.theme.Crayola

@Composable
fun UsernameTextField(username: MutableState<String> = remember { mutableStateOf("") }){
    TextField(shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(), label = {
            Text("Email", color = Crayola)
        }, value = username.value,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ), onValueChange = {
            username.value = it
        })
}