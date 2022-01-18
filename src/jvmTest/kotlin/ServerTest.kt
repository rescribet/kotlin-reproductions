import com.rescribet.kotlin.reproductions.application.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ServerTest {
    @Test
    fun testStatusPages() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
                assertEquals("hit", response.headers["exception-handler"])
                assertNull(response.headers["root-handler"])
            }
        }
    }
}
