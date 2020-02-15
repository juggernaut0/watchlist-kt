package watchlist

class WatchlistConfig(
        val auth: AuthConfig,
        val data: DataConfig
)

class AuthConfig(val host: String, val port: Int? = null)

class DataConfig(
        val dataSourceClassName: String,
        val user: String,
        val password: String,
        val jdbcUrl: String
)
