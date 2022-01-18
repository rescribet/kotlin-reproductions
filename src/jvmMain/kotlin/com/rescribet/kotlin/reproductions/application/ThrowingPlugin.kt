package com.rescribet.kotlin.reproductions.application

import io.ktor.client.plugins.ResponseException
import io.ktor.client.utils.EmptyContent.status
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.ApplicationPlugin
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.Pipeline
import io.ktor.util.pipeline.PipelineContext

class NotFoundException : Exception()

// Both APIs display the same behaviour
//val ThrowingPlugin = createApplicationPlugin("ThrowingPlugin") {
//    onCall {
//        throw NotFoundException()
//    }
//}

class ThrowingPlugin(private val configuration: Configuration) {
    class Configuration

    private fun intercept(context: PipelineContext<*, ApplicationCall>) {
        // We make a request here which can fail
        throw NotFoundException()
    }

    companion object Plugin : ApplicationPlugin<Pipeline<*, ApplicationCall>, Configuration, ThrowingPlugin> {
        override val key: AttributeKey<ThrowingPlugin> = AttributeKey("ThrowingPlugin")

        override fun install(pipeline: Pipeline<*, ApplicationCall>, configure: Configuration.() -> Unit): ThrowingPlugin {
            val configuration = Configuration().apply(configure)
            val feature = ThrowingPlugin(configuration)

            pipeline.intercept(ApplicationCallPipeline.Plugins) {
                feature.intercept(this)
            }

            return feature
        }
    }
}
