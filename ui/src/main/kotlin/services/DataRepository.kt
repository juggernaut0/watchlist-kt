package services

import maxBy
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
        val a = first.load()
        val b = second.load()
        return if (a != null && b != null) {
            maxBy(a, b) { it.timestamp }
        } else {
            a ?: b
        }
    }
}
