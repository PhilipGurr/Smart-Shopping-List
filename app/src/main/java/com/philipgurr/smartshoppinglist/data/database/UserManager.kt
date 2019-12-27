package com.philipgurr.smartshoppinglist.data.database

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

private const val USER_KEY = "user_uid"

class UserManager @Inject constructor(
    private val context: Context
) {

    // TODO: Rethink approach
    fun getCurrentUser(): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        return if (firebaseUser != null) {
            val uid = firebaseUser.uid
            preferences.edit().putString(USER_KEY, uid).commit()
            uid
        } else {
            val randomUid = generateRandomUid()
            val storedUser = preferences.getString(USER_KEY, randomUid)
            if (storedUser == randomUid) {
                preferences.edit().putString(USER_KEY, randomUid).commit()
            }
            preferences.getString(USER_KEY, randomUid)!!
        }
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