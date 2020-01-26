package components

import kui.Component
import kui.Props
import kui.classes
import services.MutableWatchlistItem
import services.WatchlistService
import toDisplay

class WatchlistRow(
    private val item: MutableWatchlistItem,
    private val service: WatchlistService,
    private val removeItem: () -> Unit
) : Component() {
    private val collapse = Collapse()

    override fun render() {
        markup().div(classes("watchlist-list-table-row-box")) {
            div(Props(classes = listOf("watchlist-list-table-row"), click = { collapse.toggle() })) {
                span(classes("watchlist-list-table-name")) { +item.name }
                span(classes("watchlist-list-table-status")) { +item.status.toDisplay() }
                span(classes("watchlist-list-table-score", "watchlist-list-table-score-btns")) {
                    item.score?.let { score ->
                        for (i in 1..5) {
                            val classes = listOfNotNull(
                                "watchlist-score-btn",
                                "watchlist-score-btn-active".takeIf { i <= score })
                            span(Props(classes = classes)) {}
                        }
                    }
                }
                span(classes("watchlist-list-table-tags")) { +item.tags.joinToString() }
            }
            component(collapse) {
                slot(Collapse.Slot) {
                    component(ItemEditor(item, onChange = { service.save(); render() }))
                    button(Props(
                        classes = listOf("watchlist-btn", "watchlist-list-delete-btn"),
                        click = { removeItem() }
                    )) {
                        +"Delete Item"
                    }
                }
            }
        }
    }
}