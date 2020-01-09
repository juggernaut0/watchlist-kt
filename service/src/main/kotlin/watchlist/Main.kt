package watchlist

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import watchlist.inject.DaggerWatchlistInjector
import watchlist.inject.WatchlistModule

fun main() {
    DaggerWatchlistInjector
            .builder()
            .watchlistModule(WatchlistModule(ConfigFactory.load().extract()))
            .build()
            .app()
            .start()
}
