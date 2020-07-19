package com.vegdev.vegacademy.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.vegdev.vegacademy.IOnFragmentBackPressed
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.LearningElement
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news_element.view.*
import kotlinx.android.synthetic.main.news_single_element.view.*
import kotlinx.android.synthetic.main.news_single_element.view.title

class NewsFragment : Fragment(), IOnFragmentBackPressed {

    private lateinit var rvAdapter: FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAdapter = this.fetchNewVideos(firestore)

        new_videos_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

    }

    override fun onFragmentBackPressed(): Boolean {
        return false
    }

    private fun fetchNewVideos(firestore: FirebaseFirestore): FirestoreRecyclerAdapter<LearningElement, NewsViewHolder> {
        val query = firestore.collection("videoNews")
        val response =
            FirestoreRecyclerOptions.Builder<LearningElement>()
                .setQuery(query, LearningElement::class.java)
                .build()

        return object : FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
                return NewsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_news_element, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: NewsViewHolder,
                position: Int,
                learningElement: LearningElement
            ) {
                holder.bindElement(learningElement)
            }

        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindElement(learningElement: LearningElement) {
            Picasso.with(itemView.context).load(learningElement.src).into(itemView.src)
            itemView.title.text = learningElement.title
            itemView.desc.text = learningElement.desc

            var src = 0
            when (learningElement.cat) {
                "ceva" -> src = R.drawable.image_ceva
                "carn" -> src = R.drawable.image_carnism
                "veg" -> src = R.drawable.imageapp
            }
            itemView.cat.background = itemView.context.getDrawable(src)

        }

    }

    override fun onStart() {
        super.onStart()
        rvAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        rvAdapter.stopListening()
    }
}
