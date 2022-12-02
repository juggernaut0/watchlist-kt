import asynclite.await
import kotlinx.serialization.json.Json
import services.DataRepository
import watchlist.api.v1.Watchlist

class IpcDataRepository(private val json: Json): DataRepository {
    override suspend fun save(watchlist: Watchlist) {
        val data = json.encodeToString(Watchlist.serializer(), watchlist)
        WatchlistIpc.save(data).await()
    }

    override suspend fun load(): Watchlist? {
        return WatchlistIpc.load().await()?.let { json.decodeFromString(Watchlist.serializer(), it) }
    }
}
