package com.philipgurr.smartshoppinglist.di.modules

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {
    @Provides
    fun provideFirebaseFirestore(context: Context): FirebaseFirestore {
        FirebaseApp.initializeApp(context)
        return Firebase.firestore
    }
}