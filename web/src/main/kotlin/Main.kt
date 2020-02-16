import auth.AuthPanel
import auth.api.v1.authModule
import auth.getToken
import components.WatchlistApp
import components.WebWrapper
import components.applyWatchlistStyles
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import multiplatform.ktor.JsonSerialization
import services.CompositeDataRepository
import services.HttpDataRepository
import services.LocalStorageDataRepository
import services.WatchlistService
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    AuthPanel.Styles.apply()
    applyWatchlistStyles()
    val json = Json(JsonConfiguration.Stable, context = authModule)
    val httpClient = HttpClient(Js) {
        defaultRequest {
            url.protocol = URLProtocol.createOrDefault(window.location.protocol.trim(':'))
            url.host = window.location.hostname
            window.location.port.toIntOrNull()?.let {
                url.port = it
            }
            getToken()?.let { token -> headers.append(HttpHeaders.Authorization, "Bearer $token") }
        }
        install(JsonSerialization) {
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
