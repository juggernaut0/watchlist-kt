package components

import kui.Component
import kui.Props
import kui.classes
import services.MutableWatchlistItem
import services.Status

class ItemEditor(val item: MutableWatchlistItem, private val onChange: (() -> Unit)? = null) : Component() {
    private var itemView = object {
        var name: String
            get() = item.name
            set(value) {
                item.name = value
                onChange?.invoke()
            }
        var status: Status = Status.of(item.status) ?: Status.TO_WATCH
            set(value) {
                field = value
                item.status = status.apiValue
                onChange?.invoke()
            }
        var score: Int?
            get() = item.score
            set(value) {
                item.score = value
                render()
                onChange?.invoke()
            }
        var scoreHover: Int? = null
            set(value) {
                field = value
                render()
            }
        val tags = TagInput(item.tags, onChange = onChange)
        var notes: String
            get() = item.notes
            set(value) {
                item.notes = value
                onChange?.invoke()
            }

        fun setScore(value: Int) {
            score = if (score == value) null else value
        }
    }

    override fun render() {
        markup().div(classes("watchlist-item-editor")) {
            label(classes("watchlist-label", "watchlist-name-label")) {
                +"Name"
                inputText(classes("watchlist-input"), model = itemView::name)
            }
            label(classes("watchlist-label", "watchlist-status-label")) {
                +"Status"
                select(classes("watchlist-input"), options = Status.values().asList(), model = itemView::status)
            }
            span(classes("watchlist-label", "watchlist-score-label")) {
                +"Score"
                div {
                    for (i in 1..5) {
                        val classes =
                            if (itemView.score == null && itemView.scoreHover == null) {
                                listOf("watchlist-score-btn", "watchlist-score-btn-muted")
                            } else {
                                listOfNotNull(
                                    "watchlist-score-btn",
                                    "watchlist-score-btn-active".takeIf { i <= (itemView.scoreHover ?: itemView.score ?: 0) })
                            }
                        button(Props(
                            classes = classes,
                            click = { itemView.setScore(i) },
                            mouseenter = { itemView.scoreHover = i },
                            mouseleave = { itemView.scoreHover = null }
                        )) { }
                    }
                }
            }
            span(classes("watchlist-label", "watchlist-tags-label")) {
                +"Tags"
                component(itemView.tags)
            }
            label(classes("watchlist-label", "watchlist-notes-label")) {
                +"Notes"
                textarea(classes("watchlist-input"), model = itemView::notes)
            }
        }
    }
}