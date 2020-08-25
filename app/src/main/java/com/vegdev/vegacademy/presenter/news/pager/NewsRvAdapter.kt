package com.vegdev.vegacademy.presenter.news.pager

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

class NewsPagerRvAdapter(
    val list: MutableList<LearningElement>, val onElementClick: (LearningElement) -> Unit,
    val onCategoryClick: (LearningElement) -> Unit
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_news_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val learningElement = list[position]
        holder.bindElement(learningElement)
        holder.itemView.src.setOnClickListener { onElementClick(learningElement) }
        holder.itemView.cat.setOnClickListener { onCategoryClick(learningElement) }
    }

    override fun getItemCount(): Int = list.size

}

class NewsViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement) {
        Glide.with(itemView.context).load(learningElement.icon).into(itemView.cat)
        Glide.with(itemView.context).load(learningElement.src).into(itemView.src)
        itemView.title.text = learningElement.title
        itemView.desc.text = learningElement.desc

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
    }

}