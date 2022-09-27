package com.ayomicakes.app.component.textfield

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ayomicakes.app.ui.theme.Crayola

@Composable
fun PasswordTextField(password: MutableState<String> = remember { mutableStateOf("") }) {
    val isPasswordVisible = remember { mutableStateOf(false) }
    TextField(shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(), label = {
            Text("Password", color = Crayola)
        }, colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ), value = password.value, onValueChange = {
            password.value = it
        }, visualTransformation = if (!isPasswordVisible.value) {
            PasswordVisualTransformation()
        } else VisualTransformation.None, trailingIcon = {
            Crossfade(isPasswordVisible.value) {
                if (it) {
                    Icon(
                        modifier = Modifier.clickable {
                            isPasswordVisible.value = false
                        },
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "visible password"
                    )
                } else Icon(
                    modifier = Modifier.clickable { isPasswordVisible.value = true },
                    imageVector = Icons.Filled.VisibilityOff,
                    contentDescription = "hide password"
                )
            }
        })

}