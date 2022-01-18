# Kotlin Reproductions

StatusPage handler doesn't prevent execution of routing handlers

1. Request comes in
2. `ThrowingPlugin` throws `NotFoundException`
3. StatusPages catches and calls appropriate handler
4. Routing block is hit
