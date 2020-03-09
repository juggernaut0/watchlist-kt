package components

import kui.Component
import kui.Props
import kui.classes
import services.MutableCategory
import services.MutableWatchlistItem
import services.Status
import services.WatchlistService
import watchlist.api.v1.WatchlistStatus
import kotlin.math.abs

class WatchlistMainView(private val service: WatchlistService) : Component() {
    private var selectedCategory: MutableCategory = service.watchlist.categories.first()
    private var newItem: MutableWatchlistItem = resetItem()

    private var currentSort: Int = 0

    private fun changeCategory(category: MutableCategory) {
        selectedCategory = category
        currentSort = 0
        render()
    }

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

    private fun <R : Comparable<R>> setSort(col: Int, by: (MutableWatchlistItem) -> R) {
        if (abs(currentSort) == col) {
            currentSort = -currentSort
            selectedCategory.items.reverse()
        } else {
            currentSort = col
            selectedCategory.items.sortBy(by)
        }
        render()
    }

    private fun sortLabel(str: String, col: Int): String {
        return when (currentSort) {
            col -> "$str ▲"
            -col -> "$str ▼"
            else -> str
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
                        click = { changeCategory(category) }
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
                        span(Props(
                            classes = listOf("watchlist-list-table-name"),
                            click = { setSort(1) { it.name } }
                        )) { +sortLabel("Name", 1) }
                        span(Props(
                            classes = listOf("watchlist-list-table-status"),
                            click = { setSort(2) { Status.of(it.status)!! } }
                        )) { +sortLabel("Status", 2) }
                        span(Props(
                            classes = listOf("watchlist-list-table-score"),
                            click = { setSort(3) { -(it.score ?: 0) } }
                        )) { +sortLabel("Score", 3) }
                        span(Props(
                            classes = listOf("watchlist-list-table-tags"),
                            click = { setSort(4) { it.tags.sorted().joinToString(separator = " ") } }
                        )) { +sortLabel("Tags", 4) }
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