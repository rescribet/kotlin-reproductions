data class Bar(val a: String)

@JsExport
data class Foo(
    val baz: List<Bar>,
)
