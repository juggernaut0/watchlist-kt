package services

import models.Watchlist
import models.WatchlistItem

class WatchlistService(private val dataRepository: DataRepository) {
    private val items = dataRepository.load().items.toMutableList()

    fun getItems(): Watchlist {
        return Watchlist(items)
    }

    fun addItem(item: WatchlistItem) {
        items.add(item)
    }

    fun saveToDisk() {
        dataRepository.save(Watchlist(items))
    }
}