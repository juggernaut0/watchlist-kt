package components

import auth.AuthPanel
import auth.isSignedIn
import kui.Component
import kui.Props
import kui.classes
import services.WatchlistService
import kotlinx.browser.window

class WebWrapper(private val service: WatchlistService) : Component() {
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
                    a(Props(click = { WatchlistApp.state = AuthPanel() })) { +"Sign in" }
                }
            }
            component(WatchlistMainView(service))
        }
    }
}
