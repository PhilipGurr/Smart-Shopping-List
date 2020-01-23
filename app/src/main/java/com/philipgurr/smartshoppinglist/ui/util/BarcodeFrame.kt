package com.philipgurr.smartshoppinglist.ui.util

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import com.philipgurr.smartshoppinglist.R

private const val BORDER_WIDTH = 10f
private const val MAX_BORDER_WIDTH = 30f

class BarcodeFrame(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {
    var previewWidth = 0
    var previewHeight = 0
    private var widthScaleFactor = 7f
    private var heightScaleFactor = 2.5f
    var barcodeRect = RectF(
        left + (right - left) / 7f,
        top + (bottom - top) / 2.5f,
        right - (right - left) / 7f,
        bottom - (bottom - top) / 2.5f
    )
    private val rectPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    fun setPreviewSize(width: Int, height: Int) {
        previewWidth = width
        previewHeight = height
        invalidate()
    }

    private fun setColor(@ColorRes color: Int) {
        rectPaint.color = ContextCompat.getColor(context, color)
    }

    fun translateRect(rect: Rect) = RectF(
        translateX(rect.left.toFloat()),
        translateY(rect.top.toFloat()),
        translateX(rect.right.toFloat()),
        translateY(rect.bottom.toFloat())
    )

    private fun translateX(x: Float): Float = x * widthScaleFactor

    private fun translateY(y: Float): Float = y * heightScaleFactor

    fun animateBarcodeFound() {
        val growAnim = getGrowAnimation()
        val shrinkAnim = getShrinkAnimation()
        AnimatorSet().apply {
            playSequentially(growAnim, shrinkAnim)
            start()
        }
    }

    private fun getGrowAnimation(): ValueAnimator? {
        val growAnim = ValueAnimator.ofFloat(BORDER_WIDTH, MAX_BORDER_WIDTH).apply {
            duration = 1000
            addUpdateListener {
                rectPaint.strokeWidth = animatedValue as Float
                invalidate()
            }
            addListener(
                onStart = { setColor(R.color.colorPrimary) }
            )
        }
        return growAnim
    }

    private fun getShrinkAnimation(): ValueAnimator {
        val shrinkAnim = ValueAnimator.ofFloat(MAX_BORDER_WIDTH, BORDER_WIDTH).apply {
            duration = 1000
            addUpdateListener {
                rectPaint.strokeWidth = animatedValue as Float
                invalidate()
            }
            addListener(
                onEnd = { setColor(R.color.white) }
            )
        }
        return shrinkAnim
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        barcodeRect = RectF(
            left + (right - left) / 7f,
            top + (bottom - top) / 2.5f,
            right - (right - left) / 7f,
            bottom - (bottom - top) / 2.5f
        )

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        if (previewWidth > 0 && previewHeight > 0) {
            widthScaleFactor = width.toFloat() / previewWidth
            heightScaleFactor = height.toFloat() / previewHeight
        }

        canvas?.drawRoundRect(barcodeRect, 5f, 5f, rectPaint)
    }
}