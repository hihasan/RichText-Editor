package com.hihasan.richtexteditor.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hihasan.richtexteditor.R

class RoundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    View(context, attrs, defStyleAttr) {
    private var mPaint: Paint? = null
    private var backgroundColor = 0
    private var isSelected = false
    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.roundView)
        backgroundColor = ta.getColor(R.styleable.roundView_backgroundColor, Color.WHITE)
        ta.recycle()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = canvas.width
        val height = canvas.height
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = backgroundColor
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(),
            mPaint!!)
        if (isSelected) {
            mPaint!!.color = Color.WHITE
            canvas.drawLine((5 * width / 16).toFloat(),
                (height / 2).toFloat(),
                (7 * width / 16).toFloat(),
                (5 * height / 8).toFloat(),
                mPaint!!)
            canvas.drawLine((7 * width / 16).toFloat(),
                (5 * height / 8).toFloat(),
                (11 * width / 16).toFloat(),
                (3 * height / 8).toFloat(),
                mPaint!!)
        }
    }

    fun getBackgroundColor(): String {
        return String.format("#%06X", 0xFFFFFF and backgroundColor)
    }

    override fun isSelected(): Boolean {
        return isSelected
    }

    override fun setSelected(selected: Boolean) {
        isSelected = selected
        invalidate()
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        this.backgroundColor = backgroundColor
        mPaint!!.color = backgroundColor
        invalidate()
    }

    init {
        init(context, attrs)
    }
}