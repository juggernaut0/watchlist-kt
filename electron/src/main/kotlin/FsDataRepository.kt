import kotlin.coroutines.suspendCoroutine

class FsDataRepository(dir: String) {
    private val filepath = path.join(dir, "data.json")

    suspend fun save(data: String) {
        try {
            mkdir(path.dirname(filepath), recursive = true)
            writeFile(filepath, data)
        } catch (e: FsException) {
            console.error(e)
        }
    }

    suspend fun load(): String? {
        val data = try {
            readFile(filepath)
        } catch (e: FsException) {
            console.error(e)
            return null
        }
        return data
    }

    private suspend fun mkdir(path: String, recursive: Boolean = false) {
        suspendCoroutine { cont ->
            fs.mkdir(path, object { val recursive = recursive }) { err ->
                cont.resumeWith(if (err != null) Result.failure(FsException(err)) else Result.success(Unit))
            }
        }
    }

    private suspend fun writeFile(path: String, data: String) {
        suspendCoroutine { cont ->
            fs.writeFile(path, data) { err ->
                cont.resumeWith(if (err != null) Result.failure(FsException(err)) else Result.success(Unit))
            }
        }
    }

    private suspend fun readFile(path: String): String {
        return suspendCoroutine { cont ->
            fs.readFile(path, options = object { val encoding = "utf8" }) { err, data ->
                val result = when {
                    err != null -> Result.failure(FsException(err))
                    data != null -> Result.success(data)
                    else -> Result.failure(IllegalStateException("readFile did not return data"))
                }
                cont.resumeWith(result)
            }
        }
    }

    private class FsException(val error: Any) : Exception(error.toString())
}