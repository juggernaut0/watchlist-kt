package services

import toDisplay
import watchlist.api.v1.WatchlistStatus

enum class Status(val apiValue: String) {
    TO_WATCH(WatchlistStatus.TO_WATCH),
    IN_PROGRESS(WatchlistStatus.IN_PROGRESS),
    ON_HOLD(WatchlistStatus.ON_HOLD),
    FINISHED(WatchlistStatus.FINISHED),
    DROPPED(WatchlistStatus.DROPPED);

    override fun toString(): String = apiValue.toDisplay()

    companion object {
        fun of(apiValue: String): Status? {
            return values().find { it.apiValue == apiValue }
        }
    }
}