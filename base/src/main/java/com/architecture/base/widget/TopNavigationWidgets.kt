package com.architecture.base.widget

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.architecture.base.R
import com.architecture.base.utils.PixelUtil

/**
 * 自定义TopBar
 */
class TopNavigationWidgets @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs) {
    private var mLastClickTime: Long = 0
    private var titleView: MediumBoldTextView? = null
    private var leftBtn: ImageButton? = null
    private var mWidth = 0
    private var mChildHeight = 0

    init {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        var statusBar = 0
        if (resourceId > 0) {
            statusBar = resources.getDimensionPixelSize(resourceId)
        }
        if (statusBar == 0) {
            statusBar = resources.getDimensionPixelSize(R.dimen.dp_30)
        }
        setPadding(0, statusBar, 0, 0)
        minimumHeight = statusBar + resources.getDimensionPixelSize(R.dimen.dp_40)
        mChildHeight = resources.getDimensionPixelSize(R.dimen.dp_40)
        mWidth = mChildHeight

        //标题
        titleView = MediumBoldTextView(getContext()).apply {
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }

        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs, R.styleable.TopNavigationWidgets, defStyleAttr, 0
            )
            val title = a.getString(R.styleable.TopNavigationWidgets_title)
            if (!title.isNullOrEmpty()) {
                setTitle(title)
            }
            titleView?.run {
                setTextColor(
                    a.getColor(
                        R.styleable.TopNavigationWidgets_title_color,
                        ContextCompat.getColor(context, R.color.white)
                    )
                )
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(
                        R.styleable.TopNavigationWidgets_title_size, PixelUtil.dip2px(18f, context)
                    ).toFloat()
                )
                setStrokeWidth(
                    a.getFloat(
                        R.styleable.TopNavigationWidgets_title_stroke_width, 0.8f
                    )
                )
            }
            val margin = a.getDimensionPixelSize(
                R.styleable.TopNavigationWidgets_textMargin, PixelUtil.dip2px(60f, context)
            )
            val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutParams.gravity = Gravity.CENTER
            layoutParams.leftMargin = margin
            layoutParams.rightMargin = margin
            addView(titleView, layoutParams)

            val hideBack = a.getBoolean(R.styleable.TopNavigationWidgets_hide_back, false)
            val resId =
                a.getResourceId(
                    R.styleable.TopNavigationWidgets_back_drawable,
                    R.mipmap.back
                )
            if (!hideBack) {
                val backMargin = a.getDimensionPixelSize(
                    R.styleable.TopNavigationWidgets_backMargin, PixelUtil.dip2px(8f, context)
                )
                addLeftButton(resId, backMargin)
            }
            val isShowLine =
                a.getBoolean(R.styleable.TopNavigationWidgets_is_show_bottom_line, false)
            if (isShowLine) {
                val lineHeight = a.getDimensionPixelSize(
                    R.styleable.TopNavigationWidgets_line_height, PixelUtil.dip2px(0.5f, context)
                )
                val lineColor = a.getColor(
                    R.styleable.TopNavigationWidgets_line_color,
                    ContextCompat.getColor(context, R.color.color_line)
                )
                addLine(lineColor, lineHeight)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                a.close()
            } else {
                a.recycle()
            }
        }
    }

    private fun addLine(lineColor: Int, lineHeight: Int) {
        val viewLine = View(context)
        viewLine.setBackgroundColor(lineColor)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, lineHeight)
        layoutParams.gravity = Gravity.BOTTOM
        addView(viewLine, layoutParams)
    }

    /**
     * 设置标题
     *
     * @param title 标题字符串
     */
    fun setTitle(title: CharSequence) {
        titleView?.text = title
    }

    fun leftBtn(): ImageButton? {
        return leftBtn
    }

    fun title(): TextView? {
        return titleView
    }

    /**
     * 添加返回按钮
     */
    private fun addLeftButton(@DrawableRes resId: Int, backMargin: Int) {
        leftBtn = ImageButton(context).apply {
            background = null
            id = R.id.btn_back
            setImageResource(resId)
            setOnClickListener(DeclaredOnClickListener(this))
        }
        val layoutParams = LayoutParams(mWidth, mChildHeight)
        layoutParams.leftMargin = backMargin
        addView(leftBtn, layoutParams)
    }

    /**
     * 反射Activity的onClick方法
     */
    private inner class DeclaredOnClickListener(private val mHostView: View) : OnClickListener {

        override fun onClick(v: View) {
            val nowTime = System.currentTimeMillis()
            if (nowTime - mLastClickTime < TIME_INTERVAL) {
                return
            }
            mLastClickTime = nowTime
            try {
                if (!mHostView.context.isRestricted) {
                    val myClass: Class<*> = View::class.java
                    val mResolvedMethod = mHostView.context.javaClass.getMethod("onClick", myClass)
                    mResolvedMethod.invoke(mHostView.context, v)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TIME_INTERVAL = 500L
    }
}