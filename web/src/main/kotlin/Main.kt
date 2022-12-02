import auth.AuthPanel
import auth.api.v1.authModule
import auth.getToken
import components.WatchlistApp
import components.WebWrapper
import components.applyWatchlistStyles
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import multiplatform.ktor.JsonSerializationClientPlugin
import services.CompositeDataRepository
import services.HttpDataRepository
import services.LocalStorageDataRepository
import services.WatchlistService

fun main() {
    AuthPanel.Styles.apply()
    applyWatchlistStyles()
    val json = Json {
        serializersModule = authModule
    }
    val httpClient = HttpClient(Js) {
        defaultRequest {
            url.protocol = URLProtocol.createOrDefault(window.location.protocol.trim(':'))
            url.host = window.location.hostname
            window.location.port.toIntOrNull()?.let {
                url.port = it
            }
            getToken()?.let { token -> headers.append(HttpHeaders.Authorization, "Bearer $token") }
        }
        install(JsonSerializationClientPlugin) {
            this.json = json
        }
    }
    val dataRepo = CompositeDataRepository(
        HttpDataRepository(httpClient),
        LocalStorageDataRepository(json)
    )
    WatchlistService(dataRepo).init { WatchlistApp.state = WebWrapper(it) }
    kui.mountComponent(document.body!!, WatchlistApp)
}
