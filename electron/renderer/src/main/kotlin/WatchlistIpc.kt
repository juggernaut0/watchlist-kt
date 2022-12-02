import kotlin.js.Promise

@JsName("watchlistIpc")
external object WatchlistIpc {
    fun save(data: String): Promise<Unit>
    fun load(): Promise<String?>
}
