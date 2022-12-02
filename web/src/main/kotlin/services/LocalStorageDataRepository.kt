package services

import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import watchlist.api.v1.Watchlist
import kotlinx.browser.localStorage

class LocalStorageDataRepository(private val json: Json, private val key: String = "watchlist-data") : DataRepository {
    override suspend fun save(watchlist: Watchlist) {
        localStorage[key] = json.encodeToString(Watchlist.serializer(), watchlist)
    }

    override suspend fun load(): Watchlist? {
        return localStorage[key]?.let { json.decodeFromString(Watchlist.serializer(), it) }
    }
}