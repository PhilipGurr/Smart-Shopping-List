package com.philipgurr.smartshoppinglist.util.extensions

import android.view.View


fun View.rotateFab(rotate: Boolean) {
    val degrees = if (rotate)
        135f
    else
        0f
    animate().setDuration(200).rotation(degrees)
}

fun View.showFabIn() {
    visibility = View.VISIBLE
    alpha = 0f
    translationY = height.toFloat()
    animate()
        .setDuration(200)
        .translationY(0f)
        .alpha(1f)
        .start()
}

fun View.showFabOut() {
    visibility = View.VISIBLE
    alpha = 1f
    translationY = 0f
    animate()
        .setDuration(200)
        .translationY(height.toFloat())
        .alpha(0f)
        .start()
}

fun View.initFab() {
    visibility = View.GONE
    translationY = height.toFloat()
    alpha = 0f
}