import com.philipgurr.domain.Product
import com.philipgurr.domain.ShoppingList
import com.philipgurr.domain.util.completedProducts
import com.philipgurr.domain.util.toId
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ShoppingListExtTest {
    private val listName = "Test List"
    private val listId = listName.hashCode().toUInt().toString()
    private val products = mutableListOf(
        Product("P1", Date(), true),
        Product("P2", Date(), false)
    )
    private val testList = ShoppingList(
        listId,
        listName,
        Date(),
        products
    )

    @Test
    fun testGenerateId() {
        assertEquals(listId, listName.toId())
    }

    @Test
    fun testGetCompletedProducts() {
        val expectedCompleted = products.filter { it.completed }
        assertEquals(expectedCompleted, testList.completedProducts())
    }
}