package services

import kotlinx.serialization.json.Json
import models.Watchlist
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.window

class DataRepository {
    fun save(watchlist: Watchlist) {
        window.localStorage["data"] = Json.stringify(Watchlist.serializer(), watchlist)
    }

    fun load(): Watchlist {
        val s = window.localStorage["data"] ?: return Watchlist(emptyList())
        return Json.nonstrict.parse(Watchlist.serializer(), s)
    }
}