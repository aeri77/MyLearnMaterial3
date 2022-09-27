@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ayomicakes.app.component.buttons

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import timber.log.Timber

@Composable
fun CaptchaButton(
    isCaptchaSuccess: Boolean?,
    isCaptchaLoading: Boolean,
    onFailure: (Exception) -> Unit,
    onSuccess: (SafetyNetApi.RecaptchaTokenResponse) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Verify Captcha")
        Crossfade(targetState = isCaptchaLoading) {
            if (!it) {
                Checkbox(checked = isCaptchaSuccess == true, onCheckedChange = {
                    SafetyNet.getClient(context)
                        .verifyWithRecaptcha("6LfcrCUiAAAAAFQsk-JAPmqqyHLrM1f_C4diesL2")
                        .addOnSuccessListener(context as Activity) { response ->
                            if (response.tokenResult?.isNotEmpty() == true) {
                                onSuccess(response)
                            }
                        }
                        .addOnFailureListener(context) { e ->
                            onFailure(e)
                        }
                })
            } else {
                CircularProgressIndicator()
            }
        }
    }
}