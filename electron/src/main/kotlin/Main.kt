import electron.BrowserWindow

private var mainWindow: BrowserWindow? = null

private fun createWindow() {
    mainWindow = BrowserWindow(object {
        val width = 1280
        val height = 720
        //val frame = false
        val webPreferences = object {
            val nodeIntegration = true
        }
    }).apply {
        loadFile("index.html")
        //openDevTools()
        setMenuBarVisibility(false)

        on("closed") { mainWindow = null }
    }
}

fun main() {
    electron.app.on("ready") { createWindow() }
    electron.app.on("window-all-closed") { electron.app.quit() }
    electron.app.on("activate") {
        if (mainWindow == null) {
            createWindow()
        }
    }
}