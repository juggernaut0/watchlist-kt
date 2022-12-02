import asynclite.async
import electron.BrowserWindow
import electron.IpcMain

private var mainWindow: BrowserWindow? = null

private fun createWindow() {
    mainWindow = BrowserWindow(object {
        val width = 1280
        val height = 720
        //val frame = false
        val webPreferences = object {
            val preload = path.join(js("__dirname") as String, "preload.js")
        }
    }).apply {
        loadFile("index.html")
        //openDevTools()
        setMenuBarVisibility(false)

        on("closed") { mainWindow = null }
    }
}

fun main() {
    val dataDir: String = js("process").env.WATCHLIST_DATA_DIR as? String ?: path.join(os.homedir(), ".watchlist")
    val dataRepository = FsDataRepository(dataDir)

    electron.app.whenReady().then {
        IpcMain.handle("save") { _: dynamic, data: String -> async { dataRepository.save(data) } }
        IpcMain.handle("load") { async { dataRepository.load() } }
        createWindow()
    }
    electron.app.on("window-all-closed") { electron.app.quit() }
    electron.app.on("activate") {
        if (mainWindow == null) {
            createWindow()
        }
    }
}