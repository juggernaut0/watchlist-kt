package components

import kui.*
import models.WatchlistItem
import services.WatchlistService

class WatchlistComponent(private val service: WatchlistService) : Component() {
    private fun addItem() {
        service.addItem(WatchlistItem("tv", "star trek"))
        render()
    }

    override fun render() {
        markup().div {
            ul {
                for (item in service.getItems().items) {
                    li {
                        +"$item"
                    }
                }
            }
            button(Props(click = { addItem() })) {
                +"Add Item"
            }
        }
    }
}
