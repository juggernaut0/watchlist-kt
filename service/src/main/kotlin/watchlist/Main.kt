package watchlist

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.StatusPages
import io.ktor.html.respondHtml
import io.ktor.http.content.resources
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import kotlinx.html.*
import org.slf4j.event.Level
import watchlist.util.WebApplicationException

fun main() {
    val context = "watchlist" // TODO from config
    embeddedServer(Jetty, 9000) {
        install(CallLogging) {
            level = Level.INFO
        }
        install(StatusPages) {
            exception<WebApplicationException> { e ->
                call.respond(e.status, e.message.orEmpty())
                throw e
            }
        }
        routing {
            route(context) {
                resources("static")
                get {
                    call.respondHtml {
                        head {
                            title { +"Watchlist" }
                            link(rel = "stylesheet", type="text/css", href="/$context/index.css")
                        }
                        body {
                            script(src = "/$context/js/main.js") {}
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
