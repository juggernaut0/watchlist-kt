@file:JsModule("electron")
package electron

import kotlin.js.Promise

external val app: App

external interface App {
    fun on(event: String, callback: () -> Unit)

    fun whenReady(): Promise<Unit>

    fun quit()
}

external class BrowserWindow(options: dynamic) {
    fun on(event: String, callback: () -> Unit)

    fun loadFile(path: String)
    fun openDevTools()

    fun setMenuBarVisibility(visibile: Boolean)
}

@JsName("ipcMain")
external object IpcMain {
    fun handle(name: String, fn: Function<Promise<*>>)
}
