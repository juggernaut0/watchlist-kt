package watchlist.inject

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.defaultRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import watchlist.WatchlistConfig
import javax.inject.Named
import javax.inject.Singleton
import javax.sql.DataSource

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

    @Provides
    @Singleton
    fun dataSource(): DataSource {
        val config = HikariConfig().apply {
            dataSourceClassName = config.data.dataSourceClassName

            addDataSourceProperty("user", config.data.user)
            addDataSourceProperty("password", config.data.password)
            addDataSourceProperty("url", config.data.jdbcUrl)
        }
        return HikariDataSource(config)
    }

    @Provides
    fun json(): Json {
        return Json(JsonConfiguration.Stable.copy(strictMode = false))
    }
}