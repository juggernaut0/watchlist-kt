import components.WatchlistComponent
import services.DataRepository
import services.WatchlistService
import kotlin.browser.window

fun main() {
    val data = DataRepository()
    val service = WatchlistService(data)
    kui.mountComponent("app", WatchlistComponent(service))
    window.addEventListener("beforeunload", { service.saveToDisk() })
}
