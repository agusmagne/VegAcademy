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
import com.vegdev.vegacademy.IOnFragmentBackPressed
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.LearningElement
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.news_single_element.view.*

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

        rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

    }

    override fun onFragmentBackPressed(): Boolean {
        return false
    }

    private fun fetchNewVideos(firestore: FirebaseFirestore): FirestoreRecyclerAdapter<LearningElement, NewsViewHolder> {
        val today = Timestamp.now()
        val query = firestore.collection("learning").document("videos").collection("ceva")
            .whereGreaterThanOrEqualTo("date", today)
        val response =
            FirestoreRecyclerOptions.Builder<LearningElement>().setQuery(query, LearningElement::class.java)
                .build()

        return object : FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
                return NewsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.news_single_element, parent, false)
                )
            }

            override fun onBindViewHolder(holder: NewsViewHolder, position: Int, learningElement: LearningElement) {
                holder.bindElement(learningElement)
            }

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

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement) {
        itemView.title.text = learningElement.title
    }

}