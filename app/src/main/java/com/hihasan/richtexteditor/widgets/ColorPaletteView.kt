package com.hihasan.richtexteditor.widgets

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.hihasan.richtexteditor.R
import java.util.*

class ColorPaletteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var binding : viewColorPaletteBinding
//    @BindView(R.id.ll_color_container)
//    var llColorContainer: LinearLayout? = null
    var selectedColor: String? = null
        private set
    private var mOnColorChangeListener: OnColorChangeListener? = null
    private val mColorList =
        Arrays.asList("#000000", "#424242", "#636363", "#9C9C94", "#CEC6CE", "#EFEFEF", "#F7F7F7",
            "#FFFFFF", "#FF0000", "#FF9C00", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#9C00FF",
            "#FF00FF", "#F7C6CE", "#FFE7CE", "#FFEFC6", "#D6EFD6", "#CEDEE7", "#CEE7F7", "#D6D6E7",
            "#E7D6DE", "#E79C9C", "#FFC69C", "#FFE79C", "#B5D6A5", "#A5C6CE", "#9CC6EF", "#B5A5D6",
            "#D6A5BD", "#E76363", "#F7AD6B", "#FFD663", "#94BD7B", "#73A5AD", "#6BADDE", "#8C7BC6",
            "#C67BA5", "#CE0000", "#E79439", "#EFC631", "#6BA54A", "#4A7B8C", "#3984C6", "#634AA5",
            "#A54A7B", "#9C0000", "#B56308", "#BD9400", "#397B21", "#104A5A", "#085294", "#311873",
            "#731842", "#630000", "#7B3900", "#846300", "#295218", "#083139", "#003163", "#21104A",
            "#4A1031")

    private fun init(context: Context) {
        val rootView: View =
            LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true)
//        ButterKnife.bind(this, rootView)
        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25f,
            resources.displayMetrics).toInt()
        val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
            resources.displayMetrics).toInt()
        var i = 0
        val size = mColorList.size
        while (i < size) {
            val roundView = RoundView(context)
            val params = LayoutParams(width, width)
            params.setMargins(margin, 0, margin, 0)
            roundView.layoutParams = params
            val color = mColorList[i]
            roundView.tag = color
            roundView.setBackgroundColor(Color.parseColor(color))
            roundView.setOnClickListener { v ->
                setSelectedColor(color)
                if (mOnColorChangeListener != null) {
                    mOnColorChangeListener!!.onColorChange(roundView.getBackgroundColor())
                }
            }
            llColorContainer!!.addView(roundView)
            i++
        }
    }

    fun setSelectedColor(selectedColor: String) {
        var selectedColor = selectedColor
        if (TextUtils.isEmpty(selectedColor)) {
            return
        }
        selectedColor = selectedColor.uppercase(Locale.getDefault())
        if (!TextUtils.isEmpty(this.selectedColor)) {
            val currentSelectedView: RoundView =
                llColorContainer!!.findViewWithTag(this.selectedColor)
            if (currentSelectedView != null) {
                currentSelectedView.isSelected =
                    this.selectedColor.equals(selectedColor, ignoreCase = true)
            }
        }
        this.selectedColor = selectedColor
        if (llColorContainer!!.findViewWithTag<View?>(selectedColor) != null) {
            llColorContainer!!.findViewWithTag<View>(selectedColor).isSelected = true
        }
    }

    fun setOnColorChangeListener(mOnColorChangeListener: OnColorChangeListener?) {
        this.mOnColorChangeListener = mOnColorChangeListener
    }

    interface OnColorChangeListener {
        fun onColorChange(color: String?)
    }

    init {
        init(context)
    }
}