package com.vegdev.vegacademy.presenter.learning.elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.LearningElement
import com.vegdev.vegacademy.utils.GenericUtils
import kotlinx.android.synthetic.main.fragment_news_element.view.*
import java.util.concurrent.TimeUnit

class ElementsAdapter(
    private val list: MutableList<LearningElement>,
    val clickListener: (LearningElement) -> Unit
) :
    RecyclerView.Adapter<ElementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_element, parent, false)
        return ElementViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val videoElement = list[position]
        holder.bindElement(videoElement)
        holder.itemView.isClickable = false
        holder.itemView.src.setOnClickListener { clickListener(videoElement) }
    }
}

class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement) {
        itemView.title.text = learningElement.title
        Glide.with(itemView.context).load(learningElement.icon).into(itemView.cat)

        val days =
            GenericUtils().getDateDifference(
                learningElement.date,
                Timestamp.now(),
                TimeUnit.DAYS
            )

        val dateDiff = if (days != 0L) {
            "Hace $days días"
        } else {
            "Hoy"
        }

        itemView.days_ago.text = dateDiff
        itemView.desc.text = learningElement.desc
        Glide.with(itemView.context).load(learningElement.src).into(itemView.src)
    }
}