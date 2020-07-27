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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.vegdev.vegacademy.IToolbar
import com.vegdev.vegacademy.IYoutubePlayer
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.LearningElement
import com.vegdev.vegacademy.utils.GenericUtils
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_news_element.view.*
import kotlinx.android.synthetic.main.fragment_news_page.*
import java.util.concurrent.TimeUnit

class NewsPageFragment(private val position: Int) : Fragment() {

    private val modelsUtils = ModelsUtils()
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<LearningElement, NewsViewHolder>
    private lateinit var firestore: FirebaseFirestore
    private var youtubePlayer: IYoutubePlayer? = null
    private var toolbar: IToolbar? = null

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
            {
                youtubePlayer?.openYoutubePlayer(it.link)
                youtubePlayer?.setYoutubePlayerState(true)
            },
            {
                val category = modelsUtils.createCategoryByCollection(it)
                findNavController().navigate(
                    NewsFragmentDirections.actionNavigationNewsToNavigationVideos(
                        category
                    )
                )
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
            toolbar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        youtubePlayer = null
        toolbar = null
    }

    private fun fetchNewLearningElements(
        firestore: FirebaseFirestore,
        newsCollection: String,
        openYoutubeListener: (LearningElement) -> Unit,
        openCategoryListener: (String) -> Unit
    ): FirestoreRecyclerAdapter<LearningElement, NewsViewHolder> {
        val query = firestore.collection(newsCollection)
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
                holder.itemView.src.setOnClickListener { openYoutubeListener(learningElement) }
                holder.itemView.cat.setOnClickListener { openCategoryListener(learningElement.cat) }
            }

        }
    }
}


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindElement(learningElement: LearningElement) {
        Picasso.with(itemView.context).load(learningElement.src).into(itemView.src)
        itemView.title.text = learningElement.title
        itemView.desc.text = learningElement.desc

        val days =
            GenericUtils().getDateDifference(learningElement.date, Timestamp.now(), TimeUnit.DAYS)
        val dateDiff = if (days != 0L) {
            "Hace $days días"
        } else {
            "Hoy"
        }
        itemView.days_ago.text = dateDiff

        var src = 0
        when (learningElement.cat) {
            "ceva" -> src = R.drawable.image_ceva
            "carn" -> src = R.drawable.image_carnism
            "veg" -> src = R.drawable.imageapp
            "nu" -> src = R.drawable.nutrition
        }
        itemView.cat.background = itemView.context.getDrawable(src)

    }

}