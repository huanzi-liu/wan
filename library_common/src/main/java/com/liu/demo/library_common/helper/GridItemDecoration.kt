package com.liu.demo.library_common.helper

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration constructor(builder: Builder) : RecyclerView.ItemDecoration() {

    var verticalDivider: Drawable? = builder.verticalDivider
    var horizontalDivider: Drawable? = builder.horizontalDivider
    var spanCount: Int = builder.spanCount
    var verticalSpaceSize: Int = builder.verticalSpaceSize
    var horizontalSpaceSize: Int = builder.horizontalSpaceSize
    var verticalIncludeEdge: Boolean = builder.verticalIncludeEdge
    var horizontalIncludeEdge: Boolean = builder.horizontalIncludeEdge

    companion object {
        fun newBuilder() = Builder
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (verticalSpaceSize > 0) {
            outRect.left = verticalSpaceSize - column * verticalSpaceSize / spanCount
            outRect.right = (column + 1) * verticalSpaceSize / spanCount
        } else {
            outRect.left = column * verticalSpaceSize / spanCount
            outRect.right = verticalSpaceSize - (column + 1) * verticalSpaceSize / spanCount
        }

        if (horizontalSpaceSize > 0) {
            if (position < spanCount) {
                outRect.top = horizontalSpaceSize
            }
            outRect.bottom = horizontalSpaceSize
        } else {
            if (position >= spanCount) {
                outRect.top = horizontalSpaceSize
            }
        }
//        super.getItemOffsets(outRect, view, parent, state)
    }

    fun drawLineTop(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (i >= spanCount) {
                val child = parent.getChildAt(i)
                val left = child.left
                val top = child.top - horizontalSpaceSize
                val right =
                    if (i % spanCount == spanCount + 1) child.right else child.right + verticalSpaceSize
                val bottom = top + horizontalSpaceSize

                horizontalDivider?.setBounds(left, top, right, bottom)
                horizontalDivider?.draw(c)
            }
        }
    }

    fun drawLineTopAndBottom(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (horizontalIncludeEdge && i < spanCount) {
                val left = child.left
                val top = child.top - horizontalSpaceSize
                val bottom = top + horizontalSpaceSize
                val isRight = (i + 1) % spanCount == 0
                val isFirst = childCount < spanCount && (i == childCount - 1)
                val right = if (isFirst || isRight) child.right else child.right + verticalSpaceSize
                horizontalDivider?.setBounds(left, top, right, bottom)
                horizontalDivider?.draw(c)
            }

            val maxLines =
                if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            val isLast = i / spanCount == maxLines - 1
            val isLastSecond = (i / spanCount == maxLines - 2) && (i + spanCount >= childCount)
            val isLastEdge = horizontalIncludeEdge && isLast
            if (isLastEdge || isLastSecond) {
                val left = child.left
                val top = child.bottom
                val bottom = top + horizontalSpaceSize
                val isLastSecondEdge = isLastSecond && ((i + 1) % spanCount == 0)
                val right =
                    if (i == childCount - 1 || isLastSecondEdge) child.right else child.right + verticalSpaceSize
                horizontalDivider?.setBounds(left, top, right, bottom)
                horizontalDivider?.draw(c)
            }
        }

    }

    fun drawLineLeft(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount)
            if (childCount % spanCount != 0) {
                val child = parent.getChildAt(i)
                val left = child.left - verticalSpaceSize
                val top = child.top
                val right = left + verticalSpaceSize
                val bottom = child.bottom

                verticalDivider?.setBounds(left, top, right, bottom)
                verticalDivider?.draw(c)
            }
    }

    fun drawLineLeftAndRight(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val maxLines =
                if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            val isLast = i / spanCount == maxLines - 1
            if (i % spanCount == 0) {
                val left = child.left - verticalSpaceSize
                val top = child.top - horizontalSpaceSize
                val right = left + verticalSpaceSize
                val bottom = if (isLast) child.bottom + horizontalSpaceSize else child.bottom

                verticalDivider?.setBounds(left, top, right, bottom)
                verticalDivider?.draw(c)
            }
            if ((i + 1) % spanCount == 0) {
                val left = child.right
                val top = child.top - horizontalSpaceSize
                val right = left + verticalSpaceSize
                val isLastSecond = (i / spanCount == maxLines - 2) && (i + spanCount >= childCount)
                val bottom =
                    if (isLast || isLastSecond) child.bottom + horizontalSpaceSize else child.bottom

                verticalDivider?.setBounds(left, top, right, bottom)
                verticalDivider?.draw(c)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state)
        if (horizontalDivider != null && horizontalSpaceSize > 0) {
            drawLineTop(c, parent)
            drawLineTopAndBottom(c, parent)
        }
        if (verticalDivider != null && verticalSpaceSize > 0) {
            drawLineLeft(c, parent)
            if (verticalIncludeEdge) {
                drawLineLeftAndRight(c, parent)
            }
        }

    }

}

object Builder {
    var verticalDivider: Drawable? = null
    var horizontalDivider: Drawable? = null
    var spanCount: Int = 1
    var verticalSpaceSize: Int = 0
    var horizontalSpaceSize: Int = 0
    var verticalIncludeEdge: Boolean = false
    var horizontalIncludeEdge: Boolean = false

    fun verticalDivider(
        verticalDivider: Drawable?,
        verticalSpaceSize: Int,
        verticalIncludeEdge: Boolean
    ): Builder {
        this.verticalDivider = verticalDivider
        this.verticalSpaceSize = verticalSpaceSize
        this.verticalIncludeEdge = verticalIncludeEdge
        return this
    }

    fun horizontalDivider(
        horizontalDivider: Drawable?,
        horizontalSpaceSize: Int,
        horizontalIncludeEdge: Boolean
    ): Builder {
        this.horizontalDivider = horizontalDivider
        this.horizontalSpaceSize = horizontalSpaceSize
        this.horizontalIncludeEdge = horizontalIncludeEdge
        return this
    }

    fun spanCount(count: Int): Builder {
        spanCount = count
        return this
    }

    fun build() = GridItemDecoration(this)


}