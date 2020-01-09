package watchlist.api.v1

import juggernaut0.multiplatform.api.ApiRoute
import juggernaut0.multiplatform.api.Method.GET
import juggernaut0.multiplatform.api.Method.PUT
import juggernaut0.multiplatform.api.pathOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.UnitSerializer

val getMyList = ApiRoute(GET, pathOf(UnitSerializer, "/watchlist/api/v1/watchlist"), Watchlist.serializer())
val saveMyList = ApiRoute(PUT, pathOf(UnitSerializer, "/watchlist/api/v1/watchlist"), UnitSerializer, Watchlist.serializer())
val getListByName = ApiRoute(GET, pathOf(Name.serializer(), "/watchlist/api/v1/watchlist/{name}"), Watchlist.serializer())

@Serializable
class Name(val name: String)
