@file:JsModule("fs")
package fs

external fun mkdir(path: String, options: dynamic = definedExternally, callback: (Any?) -> Unit)
external fun readFile(path: String, options: dynamic = definedExternally, callback: (Any?, String?) -> Unit)
external fun writeFile(path: String, data: String, callback: (Any?) -> Unit)
