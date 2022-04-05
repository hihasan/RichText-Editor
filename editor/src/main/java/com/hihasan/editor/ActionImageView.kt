package com.hihasan.editor

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.hihasan.editor.ActionType;
import com.hihasan.editor.R;
import com.hihasan.editor.RichEditorAction

class ActionImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var mActionType: ActionType? = null
    private var mRichEditorAction: RichEditorAction? = null
    private var mContext: Context? = null
    private var enabled = true
    private var activated = true
    var enabledColor = 0
    var disabledColor = 0
    var activatedColor = 0
    var deactivatedColor = 0

    private fun init(context: Context, attrs: AttributeSet?) {
        mContext = context
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ActionImageView)
        mActionType =
            ActionType.fromInteger(ta.getInteger(R.styleable.ActionImageView_actionType, 0))
        ta.recycle()
    }

    var actionType: ActionType?
        get() = mActionType
        set(mActionType) {
            this.mActionType = mActionType
        }
    var richEditorAction: RichEditorAction?
        get() = mRichEditorAction
        set(mRichEditorAction) {
            this.mRichEditorAction = mRichEditorAction
        }

    override fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun setActivated(activated: Boolean) {
        this.activated = activated
    }

    override fun isActivated(): Boolean {
        return activated
    }

    fun command() {
        //TODO RichEditorAction can not be null
        when (mActionType) {
            ActionType.BOLD -> mRichEditorAction!!.bold()
            ActionType.ITALIC -> mRichEditorAction!!.italic()
            ActionType.UNDERLINE -> mRichEditorAction!!.underline()
            ActionType.SUBSCRIPT -> mRichEditorAction!!.subscript()
            ActionType.SUPERSCRIPT -> mRichEditorAction!!.superscript()
            ActionType.STRIKETHROUGH -> mRichEditorAction!!.strikethrough()
            ActionType.NORMAL -> mRichEditorAction!!.formatPara()
            ActionType.H1 -> mRichEditorAction!!.formatH1()
            ActionType.H2 -> mRichEditorAction!!.formatH2()
            ActionType.H3 -> mRichEditorAction!!.formatH3()
            ActionType.H4 -> mRichEditorAction!!.formatH4()
            ActionType.H5 -> mRichEditorAction!!.formatH5()
            ActionType.H6 -> mRichEditorAction!!.formatH6()
            ActionType.JUSTIFY_LEFT -> mRichEditorAction!!.justifyLeft()
            ActionType.JUSTIFY_CENTER -> mRichEditorAction!!.justifyCenter()
            ActionType.JUSTIFY_RIGHT -> mRichEditorAction!!.justifyRight()
            ActionType.JUSTIFY_FULL -> mRichEditorAction!!.justifyFull()
            ActionType.ORDERED -> mRichEditorAction!!.insertOrderedList()
            ActionType.UNORDERED -> mRichEditorAction!!.insertUnorderedList()
            ActionType.INDENT -> mRichEditorAction!!.indent()
            ActionType.OUTDENT -> mRichEditorAction!!.outdent()
            ActionType.LINE -> mRichEditorAction!!.insertHorizontalRule()
            ActionType.BLOCK_QUOTE -> mRichEditorAction!!.formatBlockquote()
            ActionType.BLOCK_CODE -> mRichEditorAction!!.formatBlockCode()
            ActionType.CODE_VIEW -> mRichEditorAction!!.codeView()
            else -> {}
        }
    }

    fun command(value: String?) {

        //case FAMILY:
        //    mEditorMenuFragment.updateFontFamilyStates(value);
        //    break;
        //case SIZE:
        //    mEditorMenuFragment.updateFontStates(ActionType.SIZE, Double.valueOf(value));
        //    break;
        //case FORE_COLOR:
        //case BACK_COLOR:
        //    mEditorMenuFragment.updateFontColorStates(type, value);
        //    break;
        //case LINE_HEIGHT:
        //    mEditorMenuFragment.updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
        //    break;
        when (mActionType) {
            ActionType.FAMILY -> {}
            ActionType.SIZE -> {}
            ActionType.LINE_HEIGHT -> {}
            ActionType. FORE_COLOR -> {}
            ActionType. BACK_COLOR -> {}
            ActionType.IMAGE -> {}
            ActionType.LINK -> {}
            ActionType.TABLE -> {}
            else -> {}
        }
    }

    fun resetStatus() {}
    fun notifyFontStyleChange(type: ActionType?, value: String?) {
        post {
            when (type) {
                ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT, ActionType.STRIKETHROUGH, ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4, ActionType.H5, ActionType.H6, ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT, ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED -> setColorFilter(
                    ContextCompat.getColor(
                        mContext!!,
                        if (java.lang.Boolean.parseBoolean(value)) activatedColor else deactivatedColor))
                else -> {}
            }
        }
    }

    init {
        init(context, attrs)
    }
}