package watchlist.handlers

import io.ktor.auth.authenticate
import io.ktor.routing.Route
import juggernaut0.multiplatform.ktor.handleApi
import watchlist.api.v1.Watchlist
import watchlist.api.v1.getListByName
import watchlist.api.v1.getMyList
import watchlist.api.v1.saveMyList
import watchlist.auth.ValidatedToken
import javax.inject.Inject

fun Route.registerRoutes(handler: ApiHandler) {
    authenticate {
        handleApi(getMyList) { handler.getMyList(auth as ValidatedToken) }
        handleApi(saveMyList) { TODO() }
    }
    handleApi(getListByName) { TODO() }
}

class ApiHandler @Inject constructor() {
    fun getMyList(auth: ValidatedToken): Watchlist {
        TODO()
    }
}