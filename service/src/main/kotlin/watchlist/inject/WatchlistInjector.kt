package watchlist.inject

import dagger.Component
import watchlist.WatchlistApp
import javax.inject.Singleton

@Component(modules = [WatchlistModule::class])
@Singleton
interface WatchlistInjector {
    fun app(): WatchlistApp
}