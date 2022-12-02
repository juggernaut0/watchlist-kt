package watchlist

import auth.token
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.jetty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
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
