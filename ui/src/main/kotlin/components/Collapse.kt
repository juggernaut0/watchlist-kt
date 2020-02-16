package components

import kui.Props
import kui.SlottedComponent
import org.w3c.dom.Element
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import kotlin.browser.document
import kotlin.browser.window

class Collapse(private var collapsed: Boolean = true): SlottedComponent<Collapse.Slot>() {
    fun collapse() {
        collapsed = true
        render()
    }

    fun expand() {
        collapsed = false
        render()
    }

    fun toggle() {
        if (collapsed) expand() else collapse()
    }

    private fun props(): Props {
        return if (collapsed) Props(attrs = mapOf("style" to "display: none")) else Props.empty
    }

    override fun render() {
        markup().div(props()) {
            slot(Slot)
        }
    }

    object Slot
}
