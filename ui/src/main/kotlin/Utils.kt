import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun String.toDisplay(): String = toLowerCase().capitalize().replace('_', ' ')

inline fun <T, K : Comparable<K>> maxBy(a: T, b: T, keyFn: (T) -> K): T {
    return if (keyFn(a) > keyFn(b)) { a } else { b }
}

fun debounce(delay: Long, block: suspend () -> Unit): () -> Unit {
    var job: Job? = null

    return {
        job?.cancel()

        job = GlobalScope.launch {
            delay(delay)
            job = null
            block()
        }
    }
}
