package watchlist

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import watchlist.inject.DaggerWatchlistInjector
import watchlist.inject.WatchlistModule

fun main() {
    val config: WatchlistConfig = ConfigFactory.load().extract()
    runMigrations(DataSourceConfig(config.data.jdbcUrl, config.data.user, config.data.password))
    DaggerWatchlistInjector
        .builder()
        .watchlistModule(WatchlistModule(config))
        .build()
        .app()
        .start()
}
