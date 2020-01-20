package services

import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import watchlist.api.v1.Watchlist
import kotlin.browser.localStorage

class LocalStorageDataRepository(private val json: Json, private val key: String = "watchlist-data") : DataRepository {
    override suspend fun save(watchlist: Watchlist) {
        localStorage[key] = json.stringify(Watchlist.serializer(), watchlist)
    }

    override suspend fun load(): Watchlist? {
        return localStorage[key]?.let { json.parse(Watchlist.serializer(), it) }
    }
}