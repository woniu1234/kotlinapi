package com.architecture.base.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.architecture.base.R

class MediumBoldTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private var mStrokeWidth = 0.8f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MediumBoldTextView)
        //字体线条的描边粗细
        mStrokeWidth = typedArray.getFloat(R.styleable.MediumBoldTextView_tv_stroke_width, 0.8f)
        paint.apply {
            // 设置画笔的描边宽度值
            strokeWidth = mStrokeWidth
            style = Paint.Style.FILL_AND_STROKE
        }
        typedArray.recycle()
    }

    fun setStrokeWidth(mStrokeWidth: Float) {
        paint.apply {
            strokeWidth = mStrokeWidth
            style = Paint.Style.FILL_AND_STROKE
        }
        invalidate()
    }
}