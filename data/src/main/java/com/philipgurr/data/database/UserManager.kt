package com.philipgurr.data.database

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

private const val USER_KEY = "user_uid"
private const val LOCAL_USER_KEY = "local_user"

typealias AuthListener = (uid: String) -> Unit

class UserManager @Inject constructor(
    context: Context
) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val authListeners = mutableListOf<AuthListener>()
    private var currentUid = ""

    init {
        authenticationStateChanged(firebaseAuth) // Initial setup to create (if necessary) and set UID
        firebaseAuth.addAuthStateListener { auth ->
            authenticationStateChanged(auth)
        }
    }

    fun addOnUserChangedListener(listener: AuthListener) {
        authListeners.add(listener)
        listener(currentUid)
    }

    fun removeOnUserChangedListener(listener: AuthListener) {
        authListeners.remove(listener)
    }

    private fun authenticationStateChanged(firebaseAuth: FirebaseAuth) {
        val firebaseUser = firebaseAuth.currentUser

        currentUid = if (firebaseUser != null) {
            val uid = firebaseUser.uid
            preferences.edit().putString(USER_KEY, uid).commit()
            uid
        } else {
            val randomUid = generateRandomUid()
            val storedUser = preferences.getString(LOCAL_USER_KEY, randomUid)
            if (storedUser == randomUid) {
                preferences.edit().putString(LOCAL_USER_KEY, randomUid).commit()
            }
            val uid = preferences.getString(LOCAL_USER_KEY, randomUid)!!
            uid
        }
        fireListeners(currentUid)
    }

    private fun fireListeners(uid: String) {
        authListeners.forEach { it(uid) }
    }

    private fun generateRandomUid(): String {
        val range = 0 until 10
        return buildString {
            for (digit in (0 until 12)) {
                append(range.random())
            }
        }
    }
}