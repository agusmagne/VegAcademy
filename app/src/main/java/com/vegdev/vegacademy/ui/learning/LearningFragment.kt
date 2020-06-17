package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegdev.vegacademy.R
import kotlinx.android.synthetic.main.fragment_learning.*

class LearningFragment : Fragment() {

    private lateinit var learningViewModel: LearningViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        learningViewModel =
                ViewModelProviders.of(this).get(LearningViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_learning, container, false)
        learningViewModel.text.observe(this, Observer {
        })


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val videos_categories = resources.obtainTypedArray(R.array.videos_categories_drawables)
        val videos_titles = resources.obtainTypedArray(R.array.videos_categories_titles)

        val articles_categories = resources.obtainTypedArray(R.array.articles_categories_drawables)
        val articles_titles = resources.obtainTypedArray(R.array.articles_categories_titles)

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LearningRvAdapter(videos_categories, videos_titles)
        }

        articles_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = LearningRvAdapter(articles_categories, articles_titles)
        }


        super.onViewCreated(view, savedInstanceState)
    }
}