package services

import watchlist.api.v1.Watchlist

interface DataRepository {
    suspend fun save(watchlist: Watchlist)
    suspend fun load(): Watchlist?
}

class CompositeDataRepository(private val first: DataRepository, private val second: DataRepository) : DataRepository {
    override suspend fun save(watchlist: Watchlist) {
        first.save(watchlist)
        second.save(watchlist)
    }

    override suspend fun load(): Watchlist? {
        return first.load() ?: second.load()
    }
}
