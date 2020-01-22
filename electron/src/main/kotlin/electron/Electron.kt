@file:JsModule("electron")
package electron

external val app: App

external class App {
    fun on(event: String, callback: () -> Unit)

    fun quit()
}

external class BrowserWindow(options: dynamic) {
    fun on(event: String, callback: () -> Unit)

    fun loadFile(path: String)
    fun openDevTools()

    fun setMenuBarVisibility(visibile: Boolean)
}