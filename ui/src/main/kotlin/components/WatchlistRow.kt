package components

import kui.Component
import kui.Props
import kui.classes
import services.MutableWatchlistItem
import services.Status
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
                val status = item.status
                span(classes("watchlist-list-table-status", "watchlist-list-table-status-${statusClasses[status]}")) { +status.toDisplay() }
                span(classes("watchlist-list-table-score", "watchlist-list-table-score-btns")) {
                    item.score?.let { score ->
                        for (i in 1..5) {
                            val classes = listOfNotNull(
                                "watchlist-score-btn",
                                "watchlist-score-btn-$score".takeIf { i <= score })
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

    companion object {
        private val statusClasses = mapOf(
            Status.TO_WATCH.apiValue to "to-watch",
            Status.IN_PROGRESS.apiValue to "in-progress",
            Status.ON_HOLD.apiValue to "on-hold",
            Status.FINISHED.apiValue to "finished",
            Status.DROPPED.apiValue to "dropped"
        )
    }
}