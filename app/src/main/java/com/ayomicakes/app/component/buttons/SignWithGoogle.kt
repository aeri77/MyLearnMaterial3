package com.ayomicakes.app.component.buttons

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayomicakes.app.R
import com.ayomicakes.app.oauth.GoogleOauth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import timber.log.Timber

@Composable
fun SignWithGoogle( text: String = "Sign With Google",result : (GoogleSignInAccount?) -> Unit = {}) {
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    val authResult = GoogleOauth.handleSignInResult(task)
                    result(authResult)
                }
            }
            Timber.d("sign in result code : ${result.resultCode}")
        }
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Button(
            colors = ButtonDefaults.outlinedButtonColors(),
            border = BorderStroke(
                1.dp,
                color = MaterialTheme.colorScheme.secondary
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth(),
            onClick = {
                startForResult.launch(GoogleOauth.getGoogleLoginAuth(context).signInIntent)
            }) {
            Image(
                modifier = Modifier
                    .height(30.dp),
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text)
        }
    }
}