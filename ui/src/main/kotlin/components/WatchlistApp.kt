package components

import kui.Component
import kui.componentOf
import kui.renderOnSet
import kotlin.browser.window

object WatchlistApp : Component() {
    var state: Component
        get() = history[i]
        set(value) {
            val newi = i + 1
            if (history.size == newi) {
                history.add(value)
            } else {
                history[newi] = value
            }
            window.history.pushState(newi, "")
            i++
        }

    private var i by renderOnSet(0)
    private val history: MutableList<Component> = mutableListOf(componentOf { it.div {  } })

    init {
        window.onpopstate = { e ->
            if (e.state != null) {
                i = e.state as Int
            } else if (i < history.lastIndex) { // going forward
                i++
            }
        }
    }

    override fun render() {
        markup().component(state)
    }
}