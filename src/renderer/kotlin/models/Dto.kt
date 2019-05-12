package models

import kotlinx.serialization.Serializable

@Serializable
data class Watchlist(val items: List<WatchlistItem>)

@Serializable
data class WatchlistItem(val category: String, val title: String)