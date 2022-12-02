package watchlist.api.v1

import multiplatform.api.ApiRoute
import multiplatform.api.Method.GET
import multiplatform.api.Method.PUT
import multiplatform.api.pathOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer

val getMyList = ApiRoute(GET, pathOf(Unit.serializer(), "/watchlist/api/v1/watchlist"), Watchlist.serializer().nullable)
val saveMyList = ApiRoute(PUT, pathOf(Unit.serializer(), "/watchlist/api/v1/watchlist"), Unit.serializer(), Watchlist.serializer())
val getListByName = ApiRoute(GET, pathOf(Name.serializer(), "/watchlist/api/v1/watchlist/{name}"), Watchlist.serializer().nullable)

@Serializable
class Name(val name: String)
