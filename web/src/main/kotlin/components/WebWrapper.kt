package components

import auth.AuthPanel
import auth.isSignedIn
import io.ktor.client.HttpClient
import kui.Component
import kui.Props
import kui.classes
import services.WatchlistService
import kotlin.browser.window

class WebWrapper(private val service: WatchlistService, private val httpClient: HttpClient) : Component() {
    private fun signOut() {
        auth.signOut()
        window.localStorage.removeItem("watchlist-data")
        window.location.reload()
    }

    override fun render() {
        markup().div(classes("watchlist-wrapper")) {
            span(classes("watchlist-nav")) {
                h3 { +"Watchlist" }
                if (isSignedIn()) {
                    a(Props(click = { signOut() })) { +"Sign out" }
                } else {
                    a(Props(click = { WatchlistApp.state = AuthPanel(httpClient) })) { +"Sign in" }
                }
            }
            component(WatchlistMainView(service))
        }
    }
}
