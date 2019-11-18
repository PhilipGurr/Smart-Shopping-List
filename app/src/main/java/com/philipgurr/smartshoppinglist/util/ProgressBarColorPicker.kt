package com.philipgurr.smartshoppinglist.util

import com.philipgurr.smartshoppinglist.R

object ProgressBarColorPicker {

    fun choose(progress: Int, total: Int): Int {
        val percent = calculatePercentage(progress, total)

        return when {
            percent < 30 -> R.color.red
            percent in 30 until 100 -> R.color.orange
            else -> R.color.green
        }
    }

    private fun calculatePercentage(progress: Int, total: Int) =
        (progress * 100.0f / total).toInt()
}