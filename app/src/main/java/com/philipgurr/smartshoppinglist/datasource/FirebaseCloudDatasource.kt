package com.philipgurr.smartshoppinglist.datasource

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.util.extensions.await
import javax.inject.Inject

class FirebaseCloudDatasource @Inject constructor() : Datasource<ShoppingList> {

    private val database = Firebase.firestore

    override suspend fun get(name: String): ShoppingList {
        val shoppingListDocument = getShoppingListDocument(name)
        return parseDocument(shoppingListDocument)
    }

    private fun getShoppingListDocument(name: String) = database
            .collection("user")
            .document("test")
            .collection("shoppinglists")
            .document(name)

    private suspend fun parseDocument(document: DocumentReference): ShoppingList {
        val documentSnapshot = document.get().await()
        return documentSnapshot.toObject(ShoppingList::class.java)!!
    }

    override suspend fun getAll(): List<ShoppingList> {
        val snapshots = database
            .collection("user")
            .document("test")
            .collection("shoppinglists")
            .get().await()
        return snapshots.toObjects(ShoppingList::class.java)
    }

    override suspend fun insert(value: ShoppingList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertAll(values: List<ShoppingList>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}