package services

import auth.api.v1.authModule
import auth.getToken
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import juggernaut0.multiplatform.ktor.JsonSerialization
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import watchlist.api.v1.WatchlistStatus
import kotlin.browser.window

class WatchlistService {
    private val httpClient = HttpClient(Js) {
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

    val watchlist = loadFromLocal() ?: loadFromWeb() ?: createNew()

    private fun loadFromLocal(): MutableWatchlist? {
        return null // TODO
    }

    private fun loadFromWeb(): MutableWatchlist? {
        return null // TODO
    }

    private fun createNew(): MutableWatchlist {
        return MutableWatchlist(
            mutableListOf(
                MutableCategory("TV", mutableListOf(
                    MutableWatchlistItem("The Expanse", 4, WatchlistStatus.FINISHED, mutableListOf("scifi"), ""),
                    MutableWatchlistItem("Rick & Morty", null, WatchlistStatus.ON_HOLD, mutableListOf("scifi", "comedy"), "")
                )),
                MutableCategory("Movies", mutableListOf()),
                MutableCategory("Anime", mutableListOf()),
                MutableCategory("Games", mutableListOf())
            )
        )
    }
}