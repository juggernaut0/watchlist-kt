import components.WatchlistMainView
import components.applyWatchlistStyles
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import services.WatchlistService
import kotlin.browser.document

fun main() {
    applyWatchlistStyles()
    val json = Json(JsonConfiguration.Stable.copy(strictMode = false, encodeDefaults = false))
    val dataDir: String = js("process").env.WATCHLIST_DATA_DIR as? String ?: path.join(os.homedir(), ".watchlist")
    val dataRepo = FsDataRepository(json, dir = dataDir)
    WatchlistService(dataRepo).init { service ->
        kui.mountComponent(document.body!!, WatchlistMainView(service))
    }
}
