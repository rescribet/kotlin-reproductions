package com.rescribet.kotlin.reproductions.application

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.AttributeKey
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
        div {
            id = "root"
        }
        script(src = "/static/kotlin-reproductions.js") {}
    }
}

fun main() {
    embeddedServer(CIO, port = 8080, host = "127.0.0.1") {
        routing {
            get("/card/pikachu") {
                val card = PokemonCard(name = "pikachu")
                call.attributes.put(AttributeKey("selected-card"), card.name)

                call.respond(Json.encodeToString(card))
            }
            get("/") {

                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}
