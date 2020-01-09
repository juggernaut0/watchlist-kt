import auth.AuthPanel
import auth.api.v1.authModule
import auth.getToken
import components.WatchlistApp
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import juggernaut0.multiplatform.ktor.JsonSerialization
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    AuthPanel.Styles.apply()
    val httpClient = HttpClient(Js) {
        defaultRequest {
            url.protocol = URLProtocol.createOrDefault(window.location.protocol.trim(':'))
            url.host = window.location.hostname
            window.location.port.toIntOrNull()?.let {
                url.port = it
            }
            getToken()?.let { token -> headers.append(HttpHeaders.Authorization, token) }
        }
        install(JsonSerialization) {
            json = Json(JsonConfiguration.Stable, context = authModule)
        }
    }
    //kui.mountComponent(document.body!!, if (getToken() == null) AuthPanel(httpClient) else HelloWorld())
    kui.mountComponent(document.body!!, WatchlistApp)
}
