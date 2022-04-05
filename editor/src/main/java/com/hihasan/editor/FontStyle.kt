package com.hihasan.editor

import android.text.TextUtils
import com.google.gson.annotations.SerializedName

class FontStyle {

    @SerializedName("font-family")
    val fontFamily: String? = null

    @SerializedName("font-size")
    val fontSize = 0

    @SerializedName("font-backColor")
    val fontBackColor: String? = null

    @SerializedName("font-foreColor")
    val fontForeColor: String? = null

    @SerializedName("text-align")
    var textAlign: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                return null
            }
            val type: ActionType = when (field) {
                "left" -> ActionType.JUSTIFY_LEFT
                "center" -> ActionType.JUSTIFY_CENTER
                "right" -> ActionType.JUSTIFY_RIGHT
                "justify" -> ActionType.JUSTIFY_FULL
                else -> ActionType.JUSTIFY_FULL
            }
            return type.toString()
        }

    @SerializedName("list-style-type")
    val listStyleType: String? = null

    @SerializedName("line-height")
    val lineHeight: String? = null
        get() {
            var height = 0.0
            if (TextUtils.isEmpty(field)) {
                return height.toString()
            }
            try {
                height = java.lang.Double.valueOf(field)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return height.toString()
        }

    @SerializedName("font-bold")
    private val fontBold: String? = null

    @SerializedName("font-italic")
    private val fontItalic: String? = null

    @SerializedName("font-underline")
    private val fontUnderline: String? = null

    @SerializedName("font-subscript")
    private val fontSubscript: String? = null

    @SerializedName("font-superscript")
    private val fontSuperscript: String? = null

    @SerializedName("font-strikethrough")
    private val fontStrikethrough: String? = null

    @SerializedName("font-block")
    val fontBlock: String? = null
        get() {
            var type = ActionType.NONE
            if (TextUtils.isEmpty(field)) {
                return type.toString()
            }
            if ("p" == field) {
                type = ActionType.NORMAL
            } else if ("h1" == field) {
                type = ActionType.H1
            } else if ("h2" == field) {
                type = ActionType.H2
            } else if ("h3" == field) {
                type = ActionType.H3
            } else if ("h4" == field) {
                type = ActionType.H4
            } else if ("h5" == field) {
                type = ActionType.H5
            } else if ("h6" == field) {
                type = ActionType.H6
            }
            return type.toString()
        }

    @SerializedName("list-style")
    val listStyle: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                return null
            }
            val type: ActionType = when (field) {
                "ordered" -> {
                    ActionType.ORDERED
                }
                "unordered" -> {
                    ActionType.UNORDERED
                }
                else -> {
                    ActionType.NONE
                }
            }
            return type
        }
    val isAnchor = false
    val range: RangeBean? = null
    val ancestors: List<AncestorsBean>? = null
    val isBold: Boolean
        get() = "bold" == fontBold
    val isItalic: Boolean
        get() = "italic" == fontItalic
    val isUnderline: Boolean
        get() = "underline" == fontUnderline
    val isSubscript: Boolean
        get() = "subscript" == fontSubscript
    val isSuperscript: Boolean
        get() = "superscript" == fontSuperscript
    val isStrikethrough: Boolean
        get() = "strikethrough" == fontStrikethrough

    class RangeBean {
        /**
         * sc : {}
         * so : 4
         * ec : {}
         * eo : 4
         */
        var sc: ScBean? = null
        var so = 0
        var ec: EcBean? = null
        var eo = 0

        class ScBean
        class EcBean
    }

    class AncestorsBean
}