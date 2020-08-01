package com.vegdev.vegacademy.ui.learning

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.IProgressBar
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import com.vegdev.vegacademy.utils.ModelsUtils
import kotlinx.android.synthetic.main.fragment_learning.*
import kotlinx.android.synthetic.main.fragment_learning_element.view.*

class LearningFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var videosRvAdapter: FirestoreRecyclerAdapter<Category, CategoryViewHolder>
    private lateinit var articlesRvAdapter: FirestoreRecyclerAdapter<Category, CategoryViewHolder>
    private var progressBar: IProgressBar? = null
    private var layoutLoaded: Boolean = false
    private val modelsUtils = ModelsUtils()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutLoaded = false

        // layout is loading images
        progressBar?.currentlyLoading()

        firestore = FirebaseFirestore.getInstance()
        videosRvAdapter = fetchCategories(firestore, "videos", {
            // on finish loading images
            if (!layoutLoaded) {
                layoutLoaded = true
                progressBar?.finishedLoading()
                fragment_learning.visibility = ConstraintLayout.VISIBLE
            }
        }, { selectedCategory ->
            this.findNavController()
                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
        })
        articlesRvAdapter = fetchCategories(firestore, "art", {
            // on finish loading images
            if (!layoutLoaded) {
                layoutLoaded = true
                progressBar?.finishedLoading()
                fragment_learning.visibility = ConstraintLayout.VISIBLE
            }
        }, { selectedCategory ->
            this.findNavController()
                .navigate(LearningFragmentDirections.actionVideo(selectedCategory))
        })
        return inflater.inflate(R.layout.fragment_learning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videos_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = videosRvAdapter
        }
        articles_rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = articlesRvAdapter
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IProgressBar) {
            progressBar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        progressBar = null
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
        onImageLoaded: () -> Unit,
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
                holder.bindCategory(category) { onImageLoaded() }
                holder.itemView.setOnClickListener { onCategoryClick(category) }
            }

        }
    }
}

private class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindCategory(category: Category, imageLoaded: () -> Unit) {
        itemView.title.text = category.title
        Glide.with(itemView.context).load(category.icon)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageLoaded()
                    return false
                }
            }).into(itemView.src)
    }

}