package com.vegdev.vegacademy.ui.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.LearningElement
import kotlinx.android.synthetic.main.article_list_element.view.*
import kotlinx.android.synthetic.main.video_list_element.view.*

class VideoListRvAdapter {
    fun fetchVideos(
        firestore: FirebaseFirestore,
        firestoreCollection: String,
        listener: (LearningElement) -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder> {

        val query = firestore.collection("learning").document("videos")
            .collection(firestoreCollection)
        val response =
            FirestoreRecyclerOptions.Builder<LearningElement>()
                .setQuery(query, LearningElement::class.java).build()

        return object :
            FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): LearningElementViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_list_element, parent, false)
                return LearningElementViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holderList: LearningElementViewHolder,
                position: Int,
                learningElement: LearningElement
            ) {
                holderList.bindVideo(learningElement)
                holderList.itemView.setOnClickListener { listener(learningElement) }
            }
        }
    }

    fun fetchArticles(
        firestore: FirebaseFirestore,
        firestoreCollection: String,
        listener: (LearningElement) -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder> {

        val query = firestore.collection("learning").document("articles")
            .collection(firestoreCollection)
        val response =
            FirestoreRecyclerOptions.Builder<LearningElement>()
                .setQuery(query, LearningElement::class.java).build()

        return object :
            FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): LearningElementViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.article_list_element, parent, false)
                return LearningElementViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: LearningElementViewHolder,
                position: Int,
                article: LearningElement
            ) {
                holder.bindArticle(article)
                holder.itemView.setOnClickListener { listener(article) }
            }
        }
    }
}

class LearningElementViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bindVideo(learningElement: LearningElement) {
        itemView.video_title.text = learningElement.title
        Picasso.with(itemView.context).load(learningElement.src).into(itemView.video_image)
    }

    fun bindArticle(learningElement: LearningElement) {
        itemView.article_title.text = learningElement.title
        Picasso.with(itemView.context).load(learningElement.src).into(itemView.article_image)
    }
}
