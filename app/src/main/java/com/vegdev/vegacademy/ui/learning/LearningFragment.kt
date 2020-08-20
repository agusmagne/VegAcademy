package com.vegdev.vegacademy.ui.learning

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
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.ILayoutManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_learning.*
import kotlinx.android.synthetic.main.fragment_learning_element.view.*

class LearningFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var videosRvAdapter: FirestoreRecyclerAdapter<Category, CategoryViewHolder>
    private lateinit var articlesRvAdapter: FirestoreRecyclerAdapter<Category, CategoryViewHolder>
    private var iLayoutManager: ILayoutManager? = null
    private var isLayoutLoaded: Boolean = false
    private val modelsUtils = ModelsUtils()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        isLayoutLoaded = false

        // layout is loading images
        iLayoutManager?.currentlyLoading()

        firestore = FirebaseFirestore.getInstance()

        videosRvAdapter = fetchCategories(firestore, "videos") { selectedCategory ->
            this.findNavController()
                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
        }
        articlesRvAdapter = fetchCategories(firestore, "art") { selectedCategory ->
            this.findNavController()
                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
        }
        return inflater.inflate(R.layout.fragment_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = videosRvAdapter
        }
        articles_rv.apply {
            layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun onLayoutCompleted(state: RecyclerView.State?) {
                    super.onLayoutCompleted(state)
                    if (!isLayoutLoaded) {
                        isLayoutLoaded = true
                        iLayoutManager?.finishedLoading()
                        fragment_learning_cl.visibility = View.VISIBLE
                    }
                }
            }
            adapter = articlesRvAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILayoutManager) {
            iLayoutManager = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        iLayoutManager = null
    }

    override fun onStart() {
        super.onStart()
        videosRvAdapter.startListening()
        articlesRvAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        videosRvAdapter.stopListening()
        articlesRvAdapter.stopListening()
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
}

private class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindCategory(category: Category) {
        itemView.title.text = category.title
        Glide.with(itemView.context).load(category.icon).into(itemView.src)
    }

}