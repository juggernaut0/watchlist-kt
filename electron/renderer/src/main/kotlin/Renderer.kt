import components.WatchlistMainView
import components.applyWatchlistStyles
import kotlinx.serialization.json.Json
import services.WatchlistService
import kotlinx.browser.document

fun main() {
    applyWatchlistStyles()
    val json = Json { ignoreUnknownKeys = true; encodeDefaults = false }
    val dataRepo = IpcDataRepository(json)
    WatchlistService(dataRepo).init { service ->
        kui.mountComponent(document.body!!, WatchlistMainView(service))
    }
}
