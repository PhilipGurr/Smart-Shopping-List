package com.philipgurr.smartshoppinglist.ui.util

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ToggleFabOnScroll(
    private val fab: FloatingActionButton
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy < 0 && !fab.isShown) {
            fab.show()
        } else if (dy > 0 && fab.isShown) {
            fab.hide()
        }
    }
}