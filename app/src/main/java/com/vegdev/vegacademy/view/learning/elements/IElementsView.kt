package com.vegdev.vegacademy.view.learning.elements

import android.graphics.Bitmap
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.learning.elements.ElementViewHolder
import com.vegdev.vegacademy.view.IBaseView

interface IElementsView : IBaseView {

    fun buildRecyclerView(adapter: RecyclerView.Adapter<ElementViewHolder>)
    fun setBackgroundColor(colors: List<Int>, bitmap: Bitmap)
}