import components.WatchlistMainView
import components.applyWatchlistStyles
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import services.WatchlistService
import kotlin.browser.document

fun main() {
    applyWatchlistStyles()
    val dataRepo = FsDataRepository(Json(JsonConfiguration.Stable.copy(strictMode = false, encodeDefaults = false)))
    WatchlistService(dataRepo).init { service ->
        kui.mountComponent(document.body!!, WatchlistMainView(service))
    }
}
