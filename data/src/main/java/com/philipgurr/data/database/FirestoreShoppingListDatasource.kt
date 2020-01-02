package com.philipgurr.data.database

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.philipgurr.data.util.await
import com.philipgurr.data.util.parse
import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.util.toId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val USER_LISTS_COLLECTION_ID = "user"
private const val SHOPPING_LISTS_COLLECTION_ID = "shoppinglists"
private const val PRODUCT_LISTS_COLLECTION_ID = "products"

class FirebaseCloudShoppingListDatasource @Inject constructor(
    private val database: FirebaseFirestore,
    private val userManager: UserManager
) : ShoppingListDatasource {
    private lateinit var shoppingListCollection: CollectionReference

    private fun getShoppingListCollectionForCurrentUser() {
        shoppingListCollection = database
            .collection(USER_LISTS_COLLECTION_ID)
            .document(userManager.getCurrentUser())
            .collection(SHOPPING_LISTS_COLLECTION_ID)
    }

    override suspend fun get(name: String) = withContext(Dispatchers.IO) {
        getShoppingListCollectionForCurrentUser()
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
        getShoppingListCollectionForCurrentUser()
        val snapshots = shoppingListCollection.get().await()
        val shoppingLists = mutableListOf<ShoppingList>()

        for (snapshot in snapshots) {
            val shoppingListId = snapshot.id
            shoppingLists.add(getShoppingList(shoppingListId))
        }

        shoppingLists
    }

    override suspend fun delete(id: String) {
        withContext(Dispatchers.IO) {
            getShoppingListCollectionForCurrentUser()
            shoppingListCollection.document(id).delete()
        }
    }

    override suspend fun insert(value: ShoppingList) {
        getShoppingListCollectionForCurrentUser()
        withContext(Dispatchers.IO) {
            shoppingListCollection.document(value.id).set(value)
            addProducts(value.id, *value.products.toTypedArray())
        }
    }

    override suspend fun insertAll(values: List<ShoppingList>) {
        getShoppingListCollectionForCurrentUser()
        values.forEach { insert(it) }
    }

    override suspend fun getAllProducts(name: String): List<Product> {
        getShoppingListCollectionForCurrentUser()
        return getProducts(name)
    }

    override suspend fun insertProduct(id: String, value: Product) {
        getShoppingListCollectionForCurrentUser()
        addProducts(id, value)
    }

    private suspend fun addProducts(shoppingListId: String, vararg values: Product) {
        withContext(Dispatchers.IO) {
            for (product in values) {
                shoppingListCollection
                    .document(shoppingListId)
                    .collection(PRODUCT_LISTS_COLLECTION_ID)
                    .document(product.name.toId())
                    .set(product)
            }
        }
    }
}