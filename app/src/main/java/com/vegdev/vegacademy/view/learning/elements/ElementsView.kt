package com.vegdev.vegacademy.view.learning.elements


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.learning.ElementsInteractor
import com.vegdev.vegacademy.presenter.learning.elements.ElementViewHolder
import com.vegdev.vegacademy.presenter.learning.elements.ElementsPresenter
import com.vegdev.vegacademy.view.main.IMainView
import kotlinx.android.synthetic.main.elements_view.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class ElementsView : Fragment(), IElementsView {

    private val args: ElementsViewArgs by navArgs()
    private lateinit var category: Category
    private var presenter: ElementsPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.elements_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = args.category

        lifecycleScope.launch {
            presenter?.fetchAndBuildRecyclerView(category)
        }
        presenter?.buildAndSetBackgroundColor(category.src)

        who.setOnClickListener { presenter?.buildAndStartInstagramIntent(category.socials) }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) presenter =
            ElementsPresenter(context, this, context, ElementsInteractor())
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

    override fun setBackgroundColor(colors: List<Int>, bitmap: Bitmap) {
        this.back.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]))

        val who = "@${category.socials}"
        this.who.text = who
        this.title.text = category.title

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