package watchlist.api.v1

import multiplatform.api.ApiRoute
import multiplatform.api.Method.GET
import multiplatform.api.Method.PUT
import multiplatform.api.pathOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.UnitSerializer
import kotlinx.serialization.internal.nullable

val getMyList = ApiRoute(GET, pathOf(UnitSerializer, "/watchlist/api/v1/watchlist"), Watchlist.serializer().nullable)
val saveMyList = ApiRoute(PUT, pathOf(UnitSerializer, "/watchlist/api/v1/watchlist"), UnitSerializer, Watchlist.serializer())
val getListByName = ApiRoute(GET, pathOf(Name.serializer(), "/watchlist/api/v1/watchlist/{name}"), Watchlist.serializer().nullable)

@Serializable
class Name(val name: String)
