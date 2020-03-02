package components

import kui.Component
import kui.Props
import kui.classes
import kui.renderOnSet
import services.MutableCategory
import services.MutableWatchlistItem
import services.WatchlistService
import watchlist.api.v1.WatchlistStatus

class WatchlistMainView(private val service: WatchlistService) : Component() {
    private var selectedCategory: MutableCategory by renderOnSet(service.watchlist.categories.first())
    private var newItem: MutableWatchlistItem = resetItem()

    private fun resetItem(): MutableWatchlistItem {
        return MutableWatchlistItem("", null, WatchlistStatus.TO_WATCH, mutableListOf(), "")
    }

    private fun addItem() {
        selectedCategory.items.add(newItem)
        service.save()
        newItem = resetItem()
        render()
    }

    private fun removeItem(item: MutableWatchlistItem) {
        Modal.show("Remove Item", "Are you sure you want to remove '${item.name}'?", danger = true) {
            selectedCategory.items.remove(item)
            service.save()
            render()
        }
    }

    override fun render() {
        markup().div(classes("watchlist-container")) {
            div(classes("watchlist-category-list")) {
                for (category in service.watchlist.categories) {
                    button(Props(
                        classes = listOfNotNull(
                            "watchlist-category-btn",
                            if (category == selectedCategory) "watchlist-category-btn-active" else null
                        ),
                        click = { selectedCategory = category }
                    )) {
                        +category.name
                    }
                }
                button(Props(
                    classes = listOf("watchlist-category-btn")
                )) {
                    +"New Category..."
                }
            }
            div(classes("watchlist-list-container")) {
                h2 { +selectedCategory.name }
                div(classes("watchlist-list-table-row-box")) {
                    div(classes("watchlist-list-table-header")) {
                        span(classes("watchlist-list-table-name")) { +"Name" }
                        span(classes("watchlist-list-table-status")) { +"Status" }
                        span(classes("watchlist-list-table-score")) { +"Score" }
                        span(classes("watchlist-list-table-tags")) { +"Tags" }
                    }
                }
                div(classes("watchlist-list-table")) {
                    for (item in selectedCategory.items) {
                        component(WatchlistRow(item, service, removeItem = { removeItem(item) }))
                    }
                }
                div(classes("watchlist-list-table-row-box")) {
                    val collapse = Collapse()
                    div(Props(classes = listOf("watchlist-list-table-row"), click = { collapse.toggle() })) {
                        +"Add item..."
                    }
                    component(collapse) {
                        slot(Collapse.Slot) {
                            component(ItemEditor(newItem))
                            button(Props(
                                classes = listOf("watchlist-btn"),
                                click = { addItem() }
                            )) {
                                +"Add Item"
                            }
                        }
                    }
                }
            }
        }
    }
}