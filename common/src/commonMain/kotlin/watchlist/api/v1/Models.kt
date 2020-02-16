package watchlist.api.v1

import kotlinx.serialization.Serializable

@Serializable
data class Watchlist(
        val categories: List<Category>,
        val timestamp: Long
)

@Serializable
data class Category(
        val name: String,
        val items: List<WatchlistItem>
)

@Serializable
data class WatchlistItem(
        val name: String,
        val score: Int?,
        val status: String,
        val tags: List<String>,
        val notes: String
)

object WatchlistStatus {
    const val TO_WATCH = "TO_WATCH"
    const val IN_PROGRESS = "IN_PROGRESS"
    const val ON_HOLD = "ON_HOLD"
    const val FINISHED = "FINISHED"
    const val DROPPED = "DROPPED"
}
