package com.hihasan.richtexteditor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hihasan.editor.ActionType
import com.hihasan.richtexteditor.R
import com.hihasan.richtexteditor.databinding.FragmentEditorMenuBinding
import com.hihasan.richtexteditor.interfaces.OnActionPerformListener
import java.util.regex.Pattern

class EditorMenuFragment : Fragment() {

    private lateinit var binding : FragmentEditorMenuBinding

//    @BindView(R.id.tv_font_size)
//    var tvFontSize: TextView? = null
//
//    @BindView(R.id.tv_font_name)
//    var tvFontName: TextView? = null
//
//    @BindView(R.id.tv_font_spacing)
//    var tvFontSpacing: TextView? = null
//
//    @BindView(R.id.cpv_font_text_color)
//    var cpvFontTextColor: ColorPaletteView? = null
//
//    @BindView(R.id.cpv_highlight_color)
//    var cpvHighlightColor: ColorPaletteView? = null

    private var mActionClickListener: OnActionPerformListener? = null
    private val mViewTypeMap: Map<Int, ActionType> = object : HashMap<Int?, ActionType?>() {
        init {
            put(R.id.iv_action_bold, ActionType.BOLD)
            put(R.id.iv_action_italic, ActionType.ITALIC)
            put(R.id.iv_action_underline, ActionType.UNDERLINE)
            put(R.id.iv_action_strikethrough, ActionType.STRIKETHROUGH)
            put(R.id.iv_action_justify_left, ActionType.JUSTIFY_LEFT)
            put(R.id.iv_action_justify_center, ActionType.JUSTIFY_CENTER)
            put(R.id.iv_action_justify_right, ActionType.JUSTIFY_RIGHT)
            put(R.id.iv_action_justify_full, ActionType.JUSTIFY_FULL)
            put(R.id.iv_action_subscript, ActionType.SUBSCRIPT)
            put(R.id.iv_action_superscript, ActionType.SUPERSCRIPT)
            put(R.id.iv_action_insert_numbers, ActionType.ORDERED)
            put(R.id.iv_action_insert_bullets, ActionType.UNORDERED)
            put(R.id.iv_action_indent, ActionType.INDENT)
            put(R.id.iv_action_outdent, ActionType.OUTDENT)
            put(R.id.iv_action_code_view, ActionType.CODE_VIEW)
            put(R.id.iv_action_blockquote, ActionType.BLOCK_QUOTE)
            put(R.id.iv_action_code_block, ActionType.BLOCK_CODE)
            put(R.id.ll_normal, ActionType.NORMAL)
            put(R.id.ll_h1, ActionType.H1)
            put(R.id.ll_h2, ActionType.H2)
            put(R.id.ll_h3, ActionType.H3)
            put(R.id.ll_h4, ActionType.H4)
            put(R.id.ll_h5, ActionType.H5)
            put(R.id.ll_h6, ActionType.H6)
            put(R.id.iv_action_insert_image, ActionType.IMAGE)
            put(R.id.iv_action_insert_link, ActionType.LINK)
            put(R.id.iv_action_table, ActionType.TABLE)
            put(R.id.iv_action_line, ActionType.LINE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentEditorMenuBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

        binding.cpvFontTextColor.setOnColorChangeListener ( color ->
            if (mActionClickListener != null) {
                mActionClickListener.onActionPerform(ActionType.FORE_COLOR, color)
            }
        )

        binding.cpvHighlightColor.setOnColorChangeListener ( color ->
            if (mActionClickListener != null) {
                mActionClickListener.onActionPerform(ActionType.BACK_COLOR, color)
            }
        )
    }

    @OnClick(R.id.ll_font_size)
    fun onClickFontSize() {
        openFontSettingFragment(FontSettingFragment.TYPE_SIZE)
    }

    @OnClick(R.id.ll_line_height)
    fun onClickLineHeight() {
        openFontSettingFragment(FontSettingFragment.TYPE_LINE_HEIGHT)
    }

    @OnClick(R.id.tv_font_name)
    fun onClickFontFamily() {
        openFontSettingFragment(FontSettingFragment.TYPE_FONT_FAMILY)
    }

    @OnClick([R.id.iv_action_bold, R.id.iv_action_italic, R.id.iv_action_underline, R.id.iv_action_strikethrough, R.id.iv_action_justify_left, R.id.iv_action_justify_center, R.id.iv_action_justify_right, R.id.iv_action_justify_full, R.id.iv_action_subscript, R.id.iv_action_superscript, R.id.iv_action_insert_numbers, R.id.iv_action_insert_bullets, R.id.iv_action_indent, R.id.iv_action_outdent, R.id.iv_action_code_view, R.id.iv_action_blockquote, R.id.iv_action_code_block, R.id.ll_normal, R.id.ll_h1, R.id.ll_h2, R.id.ll_h3, R.id.ll_h4, R.id.ll_h5, R.id.ll_h6, R.id.iv_action_insert_image, R.id.iv_action_insert_link, R.id.iv_action_table, R.id.iv_action_line])
    fun onClickAction(view: View) {
        if (mActionClickListener == null) {
            return
        }
        val type: ActionType? = mViewTypeMap[view.id]
        mActionClickListener.onActionPerform(type)
    }

    private fun openFontSettingFragment(type: Int) {
        val fontSettingFragment = FontSettingFragment()
        val bundle = Bundle()
        bundle.putInt(FontSettingFragment.TYPE, type)
        fontSettingFragment.setArguments(bundle)
        fontSettingFragment.setOnResultListener { result ->
            if (mActionClickListener != null) {
                when (type) {
                    FontSettingFragment.TYPE_SIZE -> {
                        tvFontSize.setText(result)
                        mActionClickListener.onActionPerform(ActionType.SIZE, result)
                    }
                    FontSettingFragment.TYPE_LINE_HEIGHT -> {
                        tvFontSpacing.setText(result)
                        mActionClickListener.onActionPerform(ActionType.LINE_HEIGHT, result)
                    }
                    FontSettingFragment.TYPE_FONT_FAMILY -> {
                        tvFontName.setText(result)
                        mActionClickListener.onActionPerform(ActionType.FAMILY, result)
                    }
                    else -> {}
                }
            }
        }
        val fm = fragmentManager
        fm!!.beginTransaction()
            .add(R.id.fl_action, fontSettingFragment, FontSettingFragment::class.java.getName())
            .hide(this@EditorMenuFragment)
            .commit()
    }

    fun updateActionStates(type: ActionType, isActive: Boolean) {
        rootView!!.post {
            var view: View? = null
            for ((key, value) in mViewTypeMap) {
                if (value === type) {
                    view = rootView!!.findViewById(key)
                    break
                }
            }
            if (view == null) {
                return@post
            }
            when (type) {
                BOLD, ITALIC, UNDERLINE, SUBSCRIPT, SUPERSCRIPT, STRIKETHROUGH, JUSTIFY_LEFT, JUSTIFY_CENTER, JUSTIFY_RIGHT, JUSTIFY_FULL, ORDERED, CODE_VIEW, UNORDERED -> if (isActive) {
                    (view as ImageView).setColorFilter(
                        ContextCompat.getColor(context!!,
                            R.color.colorAccent))
                } else {
                    (view as ImageView).setColorFilter(
                        ContextCompat.getColor(context!!,
                            R.color.tintColor))
                }
                NORMAL, H1, H2, H3, H4, H5, H6 -> if (isActive) {
                    view.setBackgroundResource(R.drawable.round_rectangle_blue)
                } else {
                    view.setBackgroundResource(R.drawable.round_rectangle_white)
                }
                else -> {}
            }
        }
    }

    fun setActionClickListener(mActionClickListener: OnActionPerformListener?) {
        this.mActionClickListener = mActionClickListener
    }

    fun updateActionStates(type: ActionType, value: String) {
        when (type) {
            FAMILY -> updateFontFamilyStates(value)
            SIZE -> updateFontStates(ActionType.SIZE, java.lang.Double.valueOf(value))
            FORE_COLOR, BACK_COLOR -> updateFontColorStates(type, value)
            LINE_HEIGHT -> updateFontStates(ActionType.LINE_HEIGHT, java.lang.Double.valueOf(value))
            BOLD, ITALIC, UNDERLINE, SUBSCRIPT, SUPERSCRIPT, STRIKETHROUGH, JUSTIFY_LEFT, JUSTIFY_CENTER, JUSTIFY_RIGHT, JUSTIFY_FULL, NORMAL, H1, H2, H3, H4, H5, H6, ORDERED, UNORDERED -> updateActionStates(
                type,
                java.lang.Boolean.valueOf(value))
            else -> {}
        }
    }

    private fun updateFontFamilyStates(font: String) {
        rootView!!.post { tvFontName!!.text = font }
    }

    private fun updateFontStates(type: ActionType, value: Double) {
        rootView!!.post {
            when (type) {
                SIZE -> tvFontSize!!.text = value.toInt().toString()
                LINE_HEIGHT -> tvFontSpacing!!.text = value.toString()
                else -> {}
            }
        }
    }

    private fun updateFontColorStates(type: ActionType, color: String) {
        rootView!!.post {
            val selectedColor = rgbToHex(color)
            if (selectedColor != null) {
                if (type === ActionType.FORE_COLOR) {
                    cpvFontTextColor.setSelectedColor(selectedColor)
                } else if (type === ActionType.BACK_COLOR) {
                    cpvHighlightColor.setSelectedColor(selectedColor)
                }
            }
        }
    }

    companion object {
        private val PATTERN_RGB = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)")
        fun rgbToHex(rgb: String?): String? {
            val m = PATTERN_RGB.matcher(rgb)
            return if (m.matches()) {
                String.format("#%02x%02x%02x", Integer.valueOf(m.group(1)),
                    Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)))
            } else null
        }
    }
}