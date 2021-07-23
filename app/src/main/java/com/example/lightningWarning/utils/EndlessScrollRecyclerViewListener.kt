package com.example.lightningWarning.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessScrollRecyclerViewListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 && getLastVisibleItemPosition() == layoutManager.itemCount - 1) {
            onLoadMore()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        // if RecyclerView is stuck
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING
            && !recyclerView.canScrollVertically(-1)
            && !recyclerView.canScrollVertically(1)
        ) {
            onLoadMore()
        }
    }

    private fun getLastVisibleItemPosition(): Int {
        return when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val pos = IntArray(layoutManager.spanCount)
                layoutManager.findLastCompletelyVisibleItemPositions(pos)
                maxOf(0, *pos)
            }
            is GridLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            else -> {
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }
    }

    abstract fun onLoadMore()
}