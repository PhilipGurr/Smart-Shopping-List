package com.philipgurr.smartshoppinglist.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.philipgurr.smartshoppinglist.domain.Product
import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.util.extensions.await
import com.philipgurr.smartshoppinglist.util.extensions.parse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val USER_LISTS_COLLECTION_ID = "user"
private const val SHOPPING_LISTS_COLLECTION_ID = "shoppinglists"
private const val PRODUCT_LISTS_COLLECTION_ID = "products"

class FirebaseCloudDatasource @Inject constructor(
    database: FirebaseFirestore
) : Datasource<ShoppingList> {
    private val shoppingListCollection = database
        .collection(USER_LISTS_COLLECTION_ID)
        .document("test")
        .collection(SHOPPING_LISTS_COLLECTION_ID)

    override suspend fun get(name: String) = withContext(Dispatchers.IO) {
        getShoppingList(name)
    }

    private suspend fun getShoppingList(name: String): ShoppingList {
        val snapshot = shoppingListCollection.document(name).get().await()
        return snapshot.parse<ShoppingList>()!!.apply {
            id = snapshot.id
            products = getProducts(snapshot.id)
        }
    }

    private suspend fun getProducts(shoppingListId: String) =
        shoppingListCollection
            .document(shoppingListId)
            .collection(PRODUCT_LISTS_COLLECTION_ID)
            .get().await()
            .parse<Product>()

    override suspend fun getAll() = withContext(Dispatchers.IO) {
        val snapshots = shoppingListCollection.get().await()
        val shoppingLists = mutableListOf<ShoppingList>()

        for (snapshot in snapshots) {
            val shoppingListId = snapshot.id
            shoppingLists.add(getShoppingList(shoppingListId))
        }

        shoppingLists
    }

    override suspend fun insert(value: ShoppingList) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertAll(values: List<ShoppingList>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}