package com.philipgurr.data.util

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Task<T>.await(): T {
    return suspendCoroutine { continuation ->
        this.addOnSuccessListener {
            continuation.resume(it)
        }
        this.addOnFailureListener {
            continuation.resumeWithException(it)
        }
    }
}