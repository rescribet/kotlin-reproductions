package com.rescribet.kotlin.reproductions.application

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.plugins.StatusPages
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.AttributeKey

fun Application.module() {
    install(StatusPages) {
        exception<NotFoundException> { call: ApplicationCall, _ ->
            call.response.header("exception-handler", "hit")
            call.respond(HttpStatusCode.NotFound)
        }
    }

    install(ThrowingPlugin)

    routing {
        get("/") {
//            Workaround we use in production
//            if (call.attributes.contains(AttributeKey<Unit>("StatusPagesTriggered"))) {
//                return
//            }

            call.response.header("root-handler", "hit")
            call.respond(HttpStatusCode.OK)
        }
        static("/static") {
            resources()
        }
    }
}
