package watchlist.handlers

import auth.ValidatedToken
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.routing.Route
import juggernaut0.multiplatform.ktor.WebApplicationException
import juggernaut0.multiplatform.ktor.handleApi
import watchlist.api.v1.Watchlist
import watchlist.api.v1.getListByName
import watchlist.api.v1.getMyList
import watchlist.api.v1.saveMyList
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

private fun TODO(): Nothing {
    throw WebApplicationException(HttpStatusCode.NotImplemented)
}
