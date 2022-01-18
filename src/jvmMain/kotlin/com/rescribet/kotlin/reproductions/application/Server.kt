package com.rescribet.kotlin.reproductions.application

import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
        module()
    }.start(wait = true)
}
