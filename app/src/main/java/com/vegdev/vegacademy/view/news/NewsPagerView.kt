package com.vegdev.vegacademy.view.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.news.pager.NewsPagerPresenter
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder
import com.vegdev.vegacademy.view.main.IMainView
import kotlinx.android.synthetic.main.fragment_news_page.*
import kotlinx.coroutines.launch

class NewsPagerView(private val position: Int) : Fragment(), INewsPageView {

    private var presenter: NewsPagerPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch { presenter?.fetchNewsAndBuildRecyclerViews(position) }


//        rvAdapter = this.fetchNewLearningElements(
//            Firebase.firestore, newsCollection,
//            { learningElement ->
//                // on news element click
//                if (position == 1) {
//                    iLayoutManager?.toolbarOff()
//                    findNavController().navigate(
//                        NewsViewDirections.actionNavigationNewsToNavigationWebview(
//                            learningElement.link
//                        )
//                    )
//                } else {
////                    youtubePlayer?.openYoutubePlayer(learningElement)
//                }
//            },
//            { learningElement ->
//                // on element category click
//                Firebase.firestore.collection("learning")
//                    .document(if (position == 0) "videos" else "art")
//                    .collection("cat").document(learningElement.cat).get()
//                    .addOnSuccessListener { category ->
//                        findNavController().navigate(
//                            NewsViewDirections.actionNavigationNewsToNavigationVideos(
//                                category.toObject(Category::class.java)!!
//                            )
//                        )
//                    }
//
//
//            })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) presenter =
            NewsPagerPresenter(context, this, parentFragment as NewsView, context)


    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }


    override fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>) {
        news_viewpager_rv.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }
}