package com.philipgurr.data.util

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

inline fun <reified T> QuerySnapshot.parse() = toObjects(T::class.java)

inline fun <reified T> DocumentSnapshot.parse() = toObject(T::class.java)