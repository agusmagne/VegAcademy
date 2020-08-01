package com.vegdev.vegacademy.ui.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.IToolbar
import com.vegdev.vegacademy.IYoutubePlayer
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import com.vegdev.vegacademy.models.LearningElement
import com.vegdev.vegacademy.utils.GenericUtils
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_news_element.view.*
import kotlinx.android.synthetic.main.fragment_news_page.*
import java.util.concurrent.TimeUnit

class NewsPageFragment(private val position: Int) : Fragment() {

    private lateinit var rvAdapter: FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>
    private lateinit var firestore: FirebaseFirestore
    private var iToolbar: IToolbar? = null
    private var youtubePlayer: IYoutubePlayer? = null
    private val modelsUtils = ModelsUtils()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsCollection: String = if (position == 0) {
            "videoNews"
        } else {
            "articleNews"
        }

        rvAdapter = this.fetchNewLearningElements(firestore, newsCollection,
            { learningElement ->
                // on news element click
                if (position == 1) {
                    iToolbar?.toolbarOff()
                    findNavController().navigate(
                        NewsFragmentDirections.actionNavigationNewsToNavigationWebview(
                            learningElement.link
                        )
                    )
                } else {
                    youtubePlayer?.openYoutubePlayer(learningElement.link)
                }
            },
            { learningElement ->
                // on element category click
                firestore.collection("learning").document(if (position == 0) "videos" else "art")
                    .collection("cat").document(learningElement.cat).get()
                    .addOnSuccessListener { category ->
                        findNavController().navigate(
                            NewsFragmentDirections.actionNavigationNewsToNavigationVideos(
                                category.toObject(Category::class.java)!!
                            )
                        )
                    }


            })

        new_videos_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IYoutubePlayer) {
            youtubePlayer = context
        }
        if (context is IToolbar) {
            iToolbar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        youtubePlayer = null
        iToolbar = null
    }

    private fun fetchNewLearningElements(
        firestore: FirebaseFirestore,
        newsCollection: String,
        onElementClickListener: (LearningElement) -> Unit,
        openCategoryListener: (LearningElement) -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, NewsViewHolder> {
        val query = firestore.collection(newsCollection)
        val response =
            FirestoreRecyclerOptions.Builder<LearningElement>()
                .setQuery(query, LearningElement::class.java)
                .build()

        return object :
            FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): NewsViewHolder {
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
                holder.itemView.src.setOnClickListener { onElementClickListener(learningElement) }
                holder.itemView.cat.setOnClickListener { openCategoryListener(learningElement) }
            }

        }
    }
}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement) {
        Glide.with(itemView.context).load(learningElement.src).into(itemView.src)
        Glide.with(itemView.context).load(learningElement.icon).into(itemView.cat)
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