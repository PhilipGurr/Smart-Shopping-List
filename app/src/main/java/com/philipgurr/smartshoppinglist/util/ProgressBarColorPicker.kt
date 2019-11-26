package com.philipgurr.smartshoppinglist.util

import com.philipgurr.smartshoppinglist.R

object ProgressBarColorPicker {

    private const val LOWER_BOUND = 30
    private const val UPPER_BOUND = 100

    fun choose(progress: Int, total: Int): Int {

        val percent = calculatePercentage(progress, total)

        return when {
            percent < LOWER_BOUND -> R.color.red
            percent in LOWER_BOUND until UPPER_BOUND -> R.color.orange
            else -> R.color.green
        }
    }

    private fun calculatePercentage(progress: Int, total: Int) = (progress * 100.0f / total).toInt()
}