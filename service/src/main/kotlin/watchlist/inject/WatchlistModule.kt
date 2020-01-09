package watchlist.inject

import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.defaultRequest
import watchlist.WatchlistConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
class WatchlistModule(private val config: WatchlistConfig) {
    @Provides
    @Singleton
    @Named("authClient")
    fun authClient(): HttpClient {
        return HttpClient(Apache) {
            defaultRequest {
                url.host = config.auth.host
                config.auth.port?.let { url.port = it }
            }
        }
    }
}