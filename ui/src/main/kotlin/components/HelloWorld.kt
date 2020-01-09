package components

import kui.*

class HelloWorld : Component() {
    override fun render() {
        markup().div {
            +"You're logged in!"
        }
    }
}
