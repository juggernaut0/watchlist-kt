package components

import kui.*

class HelloWorld : Component() {
    private var i by renderOnSet(0)

    override fun render() {
        markup().div {
            +"Count: $i"
            button(Props(click = { i++ })) { +"Increment" }
        }
    }
}
