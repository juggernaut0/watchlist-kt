package components

import org.w3c.dom.HTMLStyleElement
import kotlinx.browser.document

private const val col2 = "${200.0/12}%"
private const val col3 = "${300.0/12}%"
private const val col4 = "${400.0/12}%"
private const val col6 = "${600.0/12}%"
private const val col8 = "${800.0/12}%"
private const val col10 = "${1000.0/12}%"

private const val borderGray = "#ccc"
private const val btnColor = "#2a4"
private const val btnSecondaryColor = "#999"
private const val btnHoverColor = "#183"
private const val btnSecondaryHoverColor = "#666"
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

.watchlist-score-btn-1 {
    background-color: hsl(0, 75%, 60%);
}
.watchlist-score-btn-2 {
    background-color: hsl(30, 75%, 60%);
}
.watchlist-score-btn-3 {
    background-color: hsl(60, 75%, 60%);
}
.watchlist-score-btn-4 {
    background-color: hsl(90, 75%, 60%);
}
.watchlist-score-btn-5 {
    background-color: hsl(120, 75%, 60%);
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

.watchlist-list-table-status-to-watch {
    color: hsl(210, 60%, 50%);
}
.watchlist-list-table-status-in-progress {
    color: hsl(270, 60%, 50%);
}
.watchlist-list-table-status-on-hold {
    color: hsl(60, 60%, 50%);
}
.watchlist-list-table-status-finished {
    color: hsl(120, 60%, 50%);
}
.watchlist-list-table-status-dropped {
    color: hsl(0, 60%, 50%);
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

private const val modalCss = """
.modal-background {
    background-color: rgba(51, 51, 51, 0.5);
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    display: none;
    justify-content: center;
    align-items: center;
}

.modal-box {
    background-color: white;
    font-family: "Segoe UI",Roboto,sans-serif;
    color: #222;
    padding: 1rem;
    border: 1px solid $borderGray;
    border-radius: 0.25rem;
    width: 80%;
}

.modal-show {
    display: flex;
}

.modal-btns {
    display: flex;
    margin: 0 -0.25rem;
}

.modal-btn {
    flex-basis: $col6;
    flex-grow: 1;
    height: 3rem;
    color: #fff;
    border: 0;
    border-radius: 0.25rem;
    margin: 0.25rem;
    font-size: 1rem;
}

.modal-btn-ok {
    background-color: $btnColor;
}

.modal-btn-ok:hover {
    background-color: $btnHoverColor;
}

.modal-btn-danger {
    background-color: $btnDangerColor;
}

.modal-btn-danger:hover {
    background-color: $btnDangerHoverColor;
}

.modal-btn-cancel {
    background-color: $btnSecondaryColor;
}

.modal-btn-cancel:hover {
    background-color: $btnSecondaryHoverColor;
}

@media only screen and (min-width: 600px) {
    .modal-box {
        width: 500px;
    }
}
"""

fun applyWatchlistStyles() {
    appendCss(css)
    appendCss(tagInputCss)
    appendCss(modalCss)
}

private fun appendCss(css: String) {
    val styleElem = document.createElement("style") as HTMLStyleElement
    styleElem.type = "text/css"
    styleElem.innerHTML = css
    document.head?.appendChild(styleElem)
}
