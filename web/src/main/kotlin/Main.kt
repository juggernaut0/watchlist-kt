import auth.AuthPanel
import auth.api.v1.authModule
import auth.getToken
import components.WatchlistApp
import components.WatchlistMainView
import components.applyWatchlistStyles
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import juggernaut0.multiplatform.ktor.JsonSerialization
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import services.WatchlistService
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    AuthPanel.Styles.apply()
    applyWatchlistStyles()
    //kui.mountComponent(document.body!!, if (getToken() == null) AuthPanel(httpClient) else HelloWorld())
    val service = WatchlistService()
    WatchlistApp.state = WatchlistMainView(service)
    kui.mountComponent(document.body!!, WatchlistApp)
}
