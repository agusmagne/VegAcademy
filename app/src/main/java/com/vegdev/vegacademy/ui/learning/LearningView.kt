package com.vegdev.vegacademy.ui.learning

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.ILayoutManager
import com.vegdev.vegacademy.IMainView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.data.models.Category
import com.vegdev.vegacademy.domain.interactor.learning.LearningInteractor
import com.vegdev.vegacademy.presenter.learning.LearningPresenter
import kotlinx.android.synthetic.main.fragment_learning.*
import kotlinx.android.synthetic.main.fragment_learning_element.view.*
import kotlinx.coroutines.launch

class LearningFragment : Fragment(), ILearningView {
    private var iLayoutManager: ILayoutManager? = null

    private lateinit var presenter: LearningPresenter
    private var iMainView: IMainView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        isLayoutLoaded = false
//
//        // layout is loading images
//        iLayoutManager?.currentlyLoading()
//
//        firestore = FirebaseFirestore.getInstance()
//
//        videosRvAdapter = fetchCategories(firestore, "videos") { selectedCategory ->
//            this.findNavController()
//                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
//        }
//        articlesRvAdapter = fetchCategories(firestore, "art") { selectedCategory ->
//            this.findNavController()
//                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
//        }
        return inflater.inflate(R.layout.fragment_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            presenter.getVideoCategoriesAdapter()
        }

//        articles_rv.apply {
//            layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
//                override fun onLayoutCompleted(state: RecyclerView.State?) {
//                    super.onLayoutCompleted(state)
//                    if (!isLayoutLoaded) {
//                        isLayoutLoaded = true
//                        iLayoutManager?.finishedLoading()
//                        fragment_learning_cl.visibility = View.VISIBLE
//                    }
//                }
//            }
//            adapter = articlesRvAdapter
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILayoutManager) {
            iLayoutManager = context
        }

        if (context is IMainView) this.presenter =
            LearningPresenter(
                context,
                this,
                context,
                findNavController(),
                LearningInteractor()
            )
    }

    override fun onDetach() {
        super.onDetach()
        iLayoutManager = null
    }

    private fun fetchCategories(
        firestore: FirebaseFirestore,
        categoryType: String,
        onCategoryClick: (Category) -> Unit
    ): FirestoreRecyclerAdapter<Category, CategoryViewHolder> {
        val query = firestore.collection("learning").document(categoryType).collection("cat")
        val response =
            FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category::class.java)
                .build()
        return object : FirestoreRecyclerAdapter<Category, CategoryViewHolder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_learning_element, parent, false)
                return CategoryViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: CategoryViewHolder,
                position: Int,
                category: Category
            ) {
                holder.bindCategory(category)
                holder.itemView.setOnClickListener { onCategoryClick(category) }
            }

        }
    }

    override fun hideLayout() {
        fragment_learning_cl.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        fragment_learning_cl.visibility = View.VISIBLE
    }

    override fun buildVideosRV(
        adapter: RecyclerView.Adapter<com.vegdev.vegacademy.presenter.learning.CategoryViewHolder>
    ) {
        videos_rv.apply {
            layoutManager = LinearLayoutManager(
                this@LearningFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.adapter = adapter
        }
    }
}

private class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindCategory(category: Category) {
        itemView.title.text = category.title
        Glide.with(itemView.context).load(category.icon).into(itemView.src)
    }

}