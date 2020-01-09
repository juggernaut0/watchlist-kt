package watchlist

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.client.HttpClient
import io.ktor.features.CallLogging
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.staticBasePackage
import io.ktor.response.respond
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import juggernaut0.multiplatform.ktor.installWebApplicationExceptionHandler
import org.slf4j.event.Level
import watchlist.auth.token
import watchlist.handlers.ApiHandler
import watchlist.handlers.registerRoutes
import javax.inject.Inject
import javax.inject.Named

class WatchlistApp @Inject constructor(
        private val apiHandler: ApiHandler,
        @Named("authClient") private val authClient: HttpClient
) {
    fun start() {
        embeddedServer(Jetty, 9000) {
            install(CallLogging) {
                level = Level.INFO
            }
            install(StatusPages) {
                installWebApplicationExceptionHandler()
                exception<NotImplementedError> {
                    call.respond(HttpStatusCode.NotImplemented, it.message.toString())
                }
            }
            install(Authentication) {
                token(httpClient = authClient)
            }
            routing {
                route("watchlist") {
                    staticBasePackage = "static"
                    resources()
                    defaultResource("index.html")
                    /*route("{name}") {
                        resources()
                        defaultResource("index.html")
                    }*/
                }
                registerRoutes(apiHandler)
            }
        }.start(wait = true)
    }
}