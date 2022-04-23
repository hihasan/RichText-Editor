package com.hihasan.editor

import android.text.TextUtils
import android.webkit.JavascriptInterface
import com.google.gson.Gson
import java.util.*

abstract class RichEditorCallback {
    private val gson: Gson = Gson()
    private var mFontStyle = FontStyle()
    private var html: String? = null
    var onGetHtmlListener: OnGetHtmlListener? = null
    private val mFontBlockGroup =
        listOf(ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
            ActionType.H5, ActionType.H6)
    private val mTextAlignGroup =
        Arrays.asList(ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
            ActionType.JUSTIFY_FULL)
    private val mListStyleGroup = Arrays.asList(ActionType.ORDERED, ActionType.UNORDERED)
    @JavascriptInterface
    fun returnHtml(html: String?) {
        this.html = html
        if (onGetHtmlListener != null) {
            onGetHtmlListener!!.getHtml(html)
        }
    }

    @JavascriptInterface
    fun updateCurrentStyle(currentStyle: String?) {
        val fontStyle: FontStyle = gson.fromJson(currentStyle, FontStyle::class.java)
        if (fontStyle != null) {
            updateStyle(fontStyle)
        }
    }

    private fun updateStyle(fontStyle: FontStyle) {
        if (mFontStyle.fontFamily == null || !mFontStyle.fontFamily
                .equals(fontStyle.fontFamily)
        ) {
            if (!TextUtils.isEmpty(fontStyle.fontFamily)) {
//                val font: String = (fontStyle.fontFamily?.split(",")?.get(0)?.replace("\"", "") ?: notifyFontStyleChange(ActionType.FAMILY, font)) as String
            }
        }
        if (mFontStyle.fontForeColor == null || !mFontStyle.fontForeColor
                .equals(fontStyle.fontForeColor)
        ) {
            if (!TextUtils.isEmpty(fontStyle.fontForeColor)) {
                notifyFontStyleChange(ActionType.FORE_COLOR, fontStyle.fontForeColor)
            }
        }
        if (mFontStyle.fontBackColor == null || !mFontStyle.fontBackColor
                .equals(fontStyle.fontBackColor)
        ) {
            if (!TextUtils.isEmpty(fontStyle.fontBackColor)) {
                notifyFontStyleChange(ActionType.BACK_COLOR, fontStyle.fontBackColor)
            }
        }
        if (mFontStyle.fontSize !== fontStyle.fontSize) {
            notifyFontStyleChange(ActionType.SIZE,
                java.lang.String.valueOf(fontStyle.fontSize))
        }
        if (mFontStyle.textAlign !== fontStyle.textAlign) {
            var i = 0
            val size = mTextAlignGroup.size
            while (i < size) {
                val type = mTextAlignGroup[i]
                notifyFontStyleChange(type, fontStyle.textAlign.toString())
                i++
            }
        }
        if (mFontStyle.lineHeight !== fontStyle.lineHeight) {
            notifyFontStyleChange(ActionType.LINE_HEIGHT,
                java.lang.String.valueOf(fontStyle.lineHeight))
        }
        if (mFontStyle.isBold !== fontStyle.isBold) {
            notifyFontStyleChange(ActionType.BOLD, java.lang.String.valueOf(fontStyle.isBold))
        }
        if (mFontStyle.isItalic !== fontStyle.isItalic) {
            notifyFontStyleChange(ActionType.ITALIC, java.lang.String.valueOf(fontStyle.isItalic))
        }
        if (mFontStyle.isUnderline !== fontStyle.isUnderline) {
            notifyFontStyleChange(ActionType.UNDERLINE,
                java.lang.String.valueOf(fontStyle.isUnderline))
        }
        if (mFontStyle.isSubscript !== fontStyle.isSubscript) {
            notifyFontStyleChange(ActionType.SUBSCRIPT,
                java.lang.String.valueOf(fontStyle.isSubscript))
        }
        if (mFontStyle.isSuperscript !== fontStyle.isSuperscript) {
            notifyFontStyleChange(ActionType.SUPERSCRIPT,
                java.lang.String.valueOf(fontStyle.isSuperscript))
        }
        if (mFontStyle.isStrikethrough !== fontStyle.isStrikethrough) {
            notifyFontStyleChange(ActionType.STRIKETHROUGH,
                java.lang.String.valueOf(fontStyle.isStrikethrough))
        }
        if (mFontStyle.fontBlock!== fontStyle.fontBlock) {
            var i = 0
            val size = mFontBlockGroup.size
            while (i < size) {
                val type = mFontBlockGroup[i]
                notifyFontStyleChange(type, fontStyle.fontBlock.toString())
                i++
            }
        }
        if (mFontStyle.listStyle !== fontStyle.listStyle) {
            var i = 0
            val size = mListStyleGroup.size
            while (i < size) {
                val type = mListStyleGroup[i]
                notifyFontStyleChange(type, fontStyle.listStyle.toString())
                i++
            }
        }
        mFontStyle = fontStyle
    }

    abstract fun notifyFontStyleChange(type: ActionType?, value: String?)
    interface OnGetHtmlListener {
        fun getHtml(html: String?)
    }
}