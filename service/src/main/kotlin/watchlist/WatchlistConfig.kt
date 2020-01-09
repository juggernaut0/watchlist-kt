package watchlist

class WatchlistConfig(
        val auth: AuthConfig
)

class AuthConfig(val host: String, val port: Int? = null)
