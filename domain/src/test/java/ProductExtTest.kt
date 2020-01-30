import com.philipgurr.domain.Product
import com.philipgurr.domain.util.displayName
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductExtTest {
    private val testProduct = Product(
        "Tasty Sandwich with Ham and Cheese and Salad"
    )

    @Test
    fun testGetDisplayname() {
        val expectedName = "Tasty Sandwich with Ham"
        assertEquals(expectedName, testProduct.displayName())
    }
}