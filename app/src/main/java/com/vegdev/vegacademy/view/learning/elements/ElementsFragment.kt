package com.vegdev.vegacademy.view.learning.elements


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialContainerTransform
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.learning.ElementsInteractor
import com.vegdev.vegacademy.presenter.learning.elements.ElementViewHolder
import com.vegdev.vegacademy.presenter.learning.elements.ElementsPresenter
import com.vegdev.vegacademy.view.main.main.MainView
import kotlinx.android.synthetic.main.elements_view.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class ElementsFragment : Fragment(), ElementsView {

    private val args: ElementsFragmentArgs by navArgs()
    private var presenter: ElementsPresenter? = null
    private lateinit var cat: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        val set = TransitionSet().setOrdering(TransitionSet.ORDERING_TOGETHER)
            .addTransition(ChangeBounds()).addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cat = arguments?.get("category") as Category

        return inflater.inflate(R.layout.elements_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        src.transitionName = cat.title + "src"
        elements_view_root.transitionName = cat.title + "back"
        Glide.with(requireContext()).load(cat.src).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }).into(src)


//        val category = args.category

        lifecycleScope.launch {
            presenter?.fetchAndBuildRecyclerView(cat)
        }
        presenter?.buildAndSetBackgroundColor(cat.src)

        who.setOnClickListener { presenter?.buildAndStartInstagramIntent(cat.socials) }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            ElementsPresenter(context, this, context, args.category, ElementsInteractor())
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
    }


    override fun buildRecyclerView(adapter: RecyclerView.Adapter<ElementViewHolder>) {
        elements_rv.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    override fun setBackgroundColor(
        colors: List<Int>,
        bitmap: Bitmap,
        socials: String,
        title: String
    ) {
        this.back.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]))
        val who = "@$socials"
        this.who.text = who
        this.title.text = title
        this.src.setImageBitmap(bitmap)
    }

    override fun hideLayout() {
        elements_view_root.visibility = View.INVISIBLE
    }

    override fun showLayout() {
        TransitionManager.beginDelayedTransition(elements_view_root)
        elements_view_root.visibility = View.VISIBLE
    }
}