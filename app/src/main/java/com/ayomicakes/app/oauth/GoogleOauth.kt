package com.ayomicakes.app.oauth

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import timber.log.Timber


object GoogleOauth {

    fun getGoogleLoginAuth(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("755660969794-81uom1dfurpfjqea368bdrac51amdb3k.apps.googleusercontent.com")
            .requestEmail()
            .requestId()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context as Activity, gso)
    }
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        try {
            return completedTask.getResult(ApiException::class.java)
        } catch (e: ApiException) {
            Timber.e("signInResult:failed code=" + e.statusCode)
        }
        return null
    }
}