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
    userManager: UserManager
) : ShoppingListDatasource {
    private lateinit var shoppingListCollection: CollectionReference

    init {
        userManager.addOnUserChangedListener { uid ->
            getShoppingListCollectionForCurrentUser(uid)
        }
    }

    private fun getShoppingListCollectionForCurrentUser(uid: String) {
        shoppingListCollection = database
            .collection(USER_LISTS_COLLECTION_ID)
            .document(uid)
            .collection(SHOPPING_LISTS_COLLECTION_ID)
    }

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

    private suspend fun getProducts(id: String): MutableList<Product> {
        val products = shoppingListCollection
            .document(id)
            .collection(PRODUCT_LISTS_COLLECTION_ID)
            .get().await()
            .parse<Product>()
        products.sortByDescending { it.created }
        return products
    }

    override suspend fun getAll() = withContext(Dispatchers.IO) {
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
            shoppingListCollection.document(id).delete()
        }
    }

    override suspend fun insert(value: ShoppingList) {
        withContext(Dispatchers.IO) {
            shoppingListCollection.document(value.id).set(value)
            addProducts(value.id, *value.products.toTypedArray())
        }
    }

    override suspend fun insertAll(values: List<ShoppingList>) {
        values.forEach { insert(it) }
    }

    override suspend fun getAllProducts(id: String): List<Product> {
        return getProducts(id)
    }

    override suspend fun insertProduct(id: String, value: Product) {
        addProducts(id, value)
    }

    private suspend fun addProducts(id: String, vararg values: Product) {
        withContext(Dispatchers.IO) {
            for (product in values) {
                shoppingListCollection
                    .document(id)
                    .collection(PRODUCT_LISTS_COLLECTION_ID)
                    .document(product.name.toId())
                    .set(product)
            }
        }
    }

    override suspend fun deleteProduct(id: String, product: Product) {
        withContext(Dispatchers.IO) {
            shoppingListCollection
                .document(id)
                .collection(PRODUCT_LISTS_COLLECTION_ID)
                .document(product.name.toId())
                .delete()
        }
    }
}