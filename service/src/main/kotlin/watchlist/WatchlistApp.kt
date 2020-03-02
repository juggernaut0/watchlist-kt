package watchlist

import auth.token
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.client.HttpClient
import io.ktor.features.CallLogging
import io.ktor.features.StatusPages
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.staticBasePackage
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import multiplatform.ktor.installWebApplicationExceptionHandler
import org.slf4j.event.Level
import watchlist.handlers.ApiHandler
import watchlist.handlers.registerRoutes
import javax.inject.Inject
import javax.inject.Named

class WatchlistApp @Inject constructor(
        private val apiHandler: ApiHandler,
        @Named("authClient") private val authClient: HttpClient
) {
    fun start() {
        embeddedServer(Jetty, 9000) { // TODO port from configuration
            install(CallLogging) {
                level = Level.INFO
            }
            install(StatusPages) {
                installWebApplicationExceptionHandler()
            }
            install(Authentication) {
                token(httpClient = authClient)
            }
            routing {
                registerRoutes(apiHandler)
                route("watchlist") {
                    staticBasePackage = "static"
                    resources()
                    defaultResource("index.html")
                    /*route("{name}") {
                        resources()
                        defaultResource("index.html")
                    }*/
                }
            }
        }.start(wait = true)
    }
}