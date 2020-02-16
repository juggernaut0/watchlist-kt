package components

import org.w3c.dom.HTMLStyleElement
import kotlin.browser.document

private const val col2 = "${200.0/12}%"
private const val col3 = "${300.0/12}%"
private const val col4 = "${400.0/12}%"
private const val col6 = "${600.0/12}%"
private const val col8 = "${800.0/12}%"
private const val col10 = "${1000.0/12}%"

private const val borderGray = "#ccc"
private const val btnColor = "#2a4"
private const val btnHoverColor = "#183"
private const val btnDangerColor = "#d33"
private const val btnDangerHoverColor = "#c22"

private const val css = """
.watchlist-wrapper {
    width: 100%;
    font-family: "Segoe UI",Roboto,sans-serif;
    color: #222;
}

.watchlist-nav {
    display: flex;
    justify-content: space-between;
}

.watchlist-container {
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    box-sizing: border-box;
    border: 1px solid $borderGray;
    border-radius: 0.5rem;
    background-color: #f7f7f7;
    font-family: "Segoe UI",Roboto,sans-serif;
    color: #222;
    overflow: hidden;
}

.watchlist-category-list {
    display: inline-block;
    flex: 0 0 100%;
    background-color: #333;
}

.watchlist-category-btn {
    width: 100%;
    height: 4rem;
    border: 0;
    border-bottom: 1px solid #666;
    background-color: inherit;
    color: #fff;
}

.watchlist-category-btn:hover {
    background-color: #999;
}

.watchlist-category-btn:focus {
    outline: none;
}

.watchlist-category-btn-active {
    font-weight: bold;
}

.watchlist-list-container {
    display: inline-block;
    padding: 1rem;
    flex: 0 0 100%;
    box-sizing: border-box;
}

.watchlist-add-item-box {
    width: 100%;
    display: block;
    box-sizing: border-box;
    padding: 0.5rem;
    border: 1px solid $borderGray;
    border-radius: 0.25rem;
}

.watchlist-item-editor {
    padding-bottom: 0.25rem;
}

.watchlist-label {
    display: inline-block;
    margin-top: 1rem;
    padding: 0 0.5rem;
    box-sizing: border-box;
    width: 100%;
    cursor: default;
}

span.watchlist-label {
    vertical-align: top;
}

.watchlist-input {
    width: 100%;
    display: inline-block;
    box-sizing: border-box;
    padding: 0.375rem 0.5rem;
    border: 1px solid $borderGray;
    border-radius: 0.25rem;
    line-height: 1.5;
    font-size: 1rem;
    color: #333;
    margin-top: 0.4rem;
    background-color: #fff;
}

textarea.watchlist-input {
    resize: vertical;
    font-family: inherit;
}

.watchlist-btn {
    display: block;
    width: 100%;
    height: 3rem;
    background-color: $btnColor;
    color: #fff;
    border: 0;
    border-radius: 0.25rem;
    margin-top: 0.5rem;
    margin-bottom: 0.25rem;
    font-size: 1rem;
}

.watchlist-btn:hover {
    background-color: $btnHoverColor;
}

.watchlist-score-btn {
    display: inline-block;
    box-sizing: border-box;
    width: 1rem;
    height: 1rem;
    background-color: transparent;
    border: 1px solid #222;
    border-radius: 0.5rem;
    margin: 0.25rem 0.5rem;
}

button.watchlist-score-btn {
    margin-top: 1rem;
    padding: 0;
}

.watchlist-score-btn-muted {
    border-color: #999;
}

.watchlist-score-btn-active {
    background-color: #222;
}

.watchlist-list-table-row-box {
    border-bottom: 1px solid $borderGray;
}

.watchlist-list-table-header {
    display: flex;
    padding: 0.25rem;
    line-height: 1.5;
    font-weight: bold;
}

.watchlist-list-table-row {
    display: flex;
    padding: 0.25rem;
    line-height: 1.5;
}

.watchlist-list-table-row:hover {
    background-color: $borderGray;
}

.watchlist-list-table-name {
    flex: 0 0 $col8;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.watchlist-list-table-status {
    flex: 0 0 $col4;
}

.watchlist-list-table-score {
    display: none;
}

.watchlist-list-table-score-btns {
    line-height: 0; /*fixes issue in electron*/
}

.watchlist-list-table-tags {
    display: none;
}

.watchlist-list-delete-btn {
    background-color: $btnDangerColor;
}

.watchlist-list-delete-btn:hover {
    background-color: $btnDangerHoverColor;
}

@media only screen and (min-width: 600px) {
    .watchlist-category-list {
        flex: 0 0 $col2;
    }
    
    .watchlist-list-container {
        flex: 0 0 $col10;
    }
    
    .watchlist-status-label {
        width: $col6;
    }
    
    .watchlist-score-label {
        width: $col6;
    }
    
    .watchlist-list-table-name {
        flex: 0 0 $col6;
    }
    
    .watchlist-list-table-status {
        flex: 0 0 $col2;
    }
    
    .watchlist-list-table-score {
        display: inline;
        flex: 0 0 $col4;
    }
}

@media only screen and (min-width: 900px) {
    .watchlist-status-label {
        width: $col6;
    }
    
    .watchlist-score-label {
        width: $col6;
    }
    
    .watchlist-name-label {
        width: $col6;
    }
    
    .watchlist-status-label {
        width: $col3;
    }
    
    .watchlist-score-label {
        width: $col3;
    }
}

@media only screen and (min-width: 1200px) {
    .watchlist-list-table-name {
        flex: 0 0 $col4;
    }
    
    .watchlist-list-table-status {
        flex: 0 0 $col2;
    }
    
    .watchlist-list-table-score {
        display: inline;
        flex: 0 0 $col2;
    }
    
    .watchlist-list-table-tags {
        display: inline;
        flex: 0 0 $col4;
    }
}

@media only screen and (min-width: 1500px) {
    .watchlist-wrapper {
        width: 1400px;
        margin: 0 auto;
    }
    
    .watchlist-tags-label {
        width: $col6;
    }
    
    .watchlist-notes-label {
        width: $col6;
    }
}
"""

private const val tagInputCss = """
.taginput {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    box-sizing: border-box;
    padding: 0.25rem 0.5rem;
    border: 1px solid $borderGray;
    border-radius: 0.25rem;
    margin-top: 0.4rem;
    background-color: #fff;
    cursor: text;
}

.taginput-tag {
    padding-left: 0.25rem;
    background-color: $btnColor;
    color: #fff;
    border-radius: 0.25rem;
    margin: 0.25rem 0;
    margin-right: 0.5rem;
    cursor: default;
}

.taginput-tag-close {
    display: inline;
    border: 0;
    background-color: inherit;
    color: inherit;
    font-size: inherit;
    padding: 0.25rem;
    border-radius: 0 0.25rem 0.25rem 0;
    margin-left: 0.25rem;
}

.taginput-tag-close:hover {
    background-color: $btnHoverColor;
}

.taginput-input {
    border: 0;
    line-height: 2;
    color: #333;
    font-size: 1rem;
    flex-grow: 1;
}

.taginput-input:focus {
    outline: none;
}
"""

fun applyWatchlistStyles() {
    appendCss(css)
    appendCss(tagInputCss)
}

private fun appendCss(css: String) {
    val styleElem = document.createElement("style") as HTMLStyleElement
    styleElem.type = "text/css"
    styleElem.innerHTML = css
    document.head?.appendChild(styleElem)
}
