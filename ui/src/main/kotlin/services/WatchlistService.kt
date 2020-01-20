package services

import debounce
import kotlinx.coroutines.*
import kotlin.js.Date

class WatchlistService(private val dataRepository: DataRepository) {
    var watchlist: MutableWatchlist = createNew()
        private set
    private val saveFn = debounce(1000) { startSave() }

    fun init(finished: (WatchlistService) -> Unit): WatchlistService {
        GlobalScope.launch {
            dataRepository.load()?.toMutable()?.let { watchlist = it }
            finished(this@WatchlistService)
        }
        return this
    }

    fun save() {
        saveFn()
    }

    private suspend fun startSave() {
        val now = Date.now().toLong()
        dataRepository.save(watchlist.toApi(now))
    }

    private fun createNew(): MutableWatchlist {
        return MutableWatchlist(
            mutableListOf(
                MutableCategory("TV", mutableListOf()),
                MutableCategory("Movies", mutableListOf()),
                MutableCategory("Anime", mutableListOf()),
                MutableCategory("Games", mutableListOf())
            )
        )
    }
}