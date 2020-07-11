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
import com.vegdev.vegacademy.models.Video
import kotlinx.android.synthetic.main.video_list_element.view.*

class VideoListRvAdapter {
    fun fetchVideos(
        firestore: FirebaseFirestore, categoryType: String, firestoreCollection: String, listener: (Video) -> Unit
    ): FirestoreRecyclerAdapter<Video, VideoListViewHolder> {

        val query = firestore.collection("learning").document(categoryType)
            .collection(firestoreCollection)
        val response =
            FirestoreRecyclerOptions.Builder<Video>().setQuery(query, Video::class.java).build()

        return object : FirestoreRecyclerAdapter<Video, VideoListViewHolder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_list_element, parent, false)
                return VideoListViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holderList: VideoListViewHolder,
                position: Int,
                video: Video
            ) {
                holderList.bindVideo(video)
                holderList.itemView.setOnClickListener { listener(video) }
            }
        }
    }
}

class VideoListViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bindVideo(video: Video) {
        itemView.video_title.text = video.title
        Picasso.with(itemView.context).load(video.src).into(itemView.video_src)
    }
}
