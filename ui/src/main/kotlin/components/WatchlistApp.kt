package components

import kui.Component
import kui.componentOf
import kui.renderOnSet

object WatchlistApp : Component() {
    var state: Component by renderOnSet(componentOf { it.div {  } })

    override fun render() {
        markup().component(state)
    }
}