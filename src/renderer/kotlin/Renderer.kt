import components.WatchlistComponent
import services.DataRepository
import services.WatchlistService

fun main() {
    val data = DataRepository()
    val service = WatchlistService(data)
    kui.mountComponent("app", WatchlistComponent(service))
}
