import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
enum class MyEnum {
    A,
    B,
    C
}

@Serializable
data class Test(val name: String)
