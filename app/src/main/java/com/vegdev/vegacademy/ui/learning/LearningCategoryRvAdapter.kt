package com.vegdev.vegacademy.ui.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.LearningElement
import kotlinx.android.synthetic.main.article_list_element.view.*

class VideoListRvAdapter {
    fun fetchVideos(
        firestore: FirebaseFirestore,
        firestoreCollection: String,
        listener: (LearningElement) -> Unit,
        onFinish: () -> Unit
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
                    .inflate(R.layout.article_list_element, parent, false)
                return LearningElementViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holderList: LearningElementViewHolder,
                position: Int,
                learningElement: LearningElement
            ) {
                holderList.bindElement(learningElement) { onFinish() }
                holderList.itemView.setOnClickListener { listener(learningElement) }
            }
        }
    }

    fun fetchArticles(
        firestore: FirebaseFirestore,
        firestoreCollection: String,
        listener: (LearningElement) -> Unit,
        onFinish: () -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, LearningElementViewHolder> {

        val query = firestore.collection("learning").document("art")
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
                holder.bindElement(article) { onFinish() }
                holder.itemView.setOnClickListener {
                    listener(article)
                }
            }
        }
    }
}

class LearningElementViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement, listener: () -> Unit) {
        itemView.article_title.text = learningElement.title
        Picasso.with(itemView.context).load(learningElement.src)
            .into(itemView.article_image, object : Callback {
                override fun onError() {}
                override fun onSuccess() {
                    listener()
                }
            })
    }
}
