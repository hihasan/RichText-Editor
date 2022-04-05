package com.hihasan.editor

import android.os.Build
import android.webkit.WebView

class RichEditorAction(private val mWebView: WebView) {
    fun undo() {
        load("javascript:undo()")
    }

    fun redo() {
        load("javascript:redo()")
    }

    fun focus() {
        load("javascript:focus()")
    }

    fun disable() {
        load("javascript:disable()")
    }

    fun enable() {
        load("javascript:enable()")
    }

    /******************** Font  */
    fun bold() {
        load("javascript:bold()")
    }

    fun italic() {
        load("javascript:italic()")
    }

    fun underline() {
        load("javascript:underline()")
    }

    fun strikethrough() {
        load("javascript:strikethrough()")
    }

    fun superscript() {
        load("javascript:superscript()")
    }

    fun subscript() {
        load("javascript:subscript()")
    }

    fun backColor(color: String) {
        load("javascript:backColor('$color')")
    }

    fun foreColor(color: String) {
        load("javascript:foreColor('$color')")
    }

    fun fontName(fontName: String) {
        load("javascript:fontName('$fontName')")
    }

    fun fontSize(foreSize: Double) {
        load("javascript:fontSize($foreSize)")
    }

    /******************** Paragraph  */
    fun justifyLeft() {
        load("javascript:justifyLeft()")
    }

    fun justifyRight() {
        load("javascript:justifyRight()")
    }

    fun justifyCenter() {
        load("javascript:justifyCenter()")
    }

    fun justifyFull() {
        load("javascript:justifyFull()")
    }

    fun insertOrderedList() {
        load("javascript:insertOrderedList()")
    }

    fun insertUnorderedList() {
        load("javascript:insertUnorderedList()")
    }

    fun indent() {
        load("javascript:indent()")
    }

    fun outdent() {
        load("javascript:outdent()")
    }

    fun formatPara() {
        load("javascript:formatPara()")
    }

    fun formatH1() {
        load("javascript:formatH1()")
    }

    fun formatH2() {
        load("javascript:formatH2()")
    }

    fun formatH3() {
        load("javascript:formatH3()")
    }

    fun formatH4() {
        load("javascript:formatH4()")
    }

    fun formatH5() {
        load("javascript:formatH5()")
    }

    fun formatH6() {
        load("javascript:formatH6()")
    }

    fun lineHeight(lineHeight: Double) {
        load("javascript:lineHeight($lineHeight)")
    }

    fun insertImageUrl(imageUrl: String) {
        load("javascript:insertImageUrl('$imageUrl')")
    }

    fun insertImageData(fileName: String, base64Str: String) {
        val imageUrl =
            "data:image/" + fileName.split("\\.").toTypedArray()[1] + ";base64," + base64Str
        load("javascript:insertImageUrl('$imageUrl')")
    }

    fun insertText(text: String) {
        load("javascript:insertText('$text')")
    }

    fun createLink(linkText: String, linkUrl: String) {
        load("javascript:createLink('$linkText','$linkUrl')")
    }

    fun unlink() {
        load("javascript:unlink()")
    }

    fun codeView() {
        load("javascript:codeView()")
    }

    fun insertTable(colCount: Int, rowCount: Int) {
        load("javascript:insertTable('" + colCount + "x" + rowCount + "')")
    }

    fun insertHorizontalRule() {
        load("javascript:insertHorizontalRule()")
    }

    fun formatBlockquote() {
        load("javascript:formatBlock('blockquote')")
    }

    fun formatBlockCode() {
        load("javascript:formatBlock('pre')")
    }

    fun insertHtml(html: String) {
        load("javascript:pasteHTML('$html')")
    }

    fun refreshHtml(
        callback: RichEditorCallback,
        onGetHtmlListener: RichEditorCallback.OnGetHtmlListener,
    ) {
        callback.onGetHtmlListener //  .setOnGetHtmlListener(onGetHtmlListener)
        load("javascript:refreshHTML()")
    }

    private fun load(trigger: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(trigger, null)
        } else {
            mWebView.loadUrl(trigger)
        }
    }
}