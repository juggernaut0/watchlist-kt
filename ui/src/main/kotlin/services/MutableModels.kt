package services

import watchlist.api.v1.Category
import watchlist.api.v1.Watchlist
import watchlist.api.v1.WatchlistItem

data class MutableWatchlist(
    val categories: MutableList<MutableCategory>
)

data class MutableCategory(
    var name: String,
    val items: MutableList<MutableWatchlistItem>
)

data class MutableWatchlistItem(
    var name: String,
    var score: Int?,
    var status: String,
    val tags: MutableList<String>,
    var notes: String
)

fun MutableWatchlist.toApi(): Watchlist = Watchlist(categories.map { it.toApi() })
fun MutableCategory.toApi(): Category = Category(name, items.map { it.toApi() })
fun MutableWatchlistItem.toApi(): WatchlistItem = WatchlistItem(name, score, status, tags, notes)
