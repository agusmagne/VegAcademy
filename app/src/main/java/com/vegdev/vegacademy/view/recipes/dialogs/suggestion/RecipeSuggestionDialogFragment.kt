package com.vegdev.vegacademy.view.recipes.dialogs.suggestion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.domain.interactor.main.dialogs.RecipeSuggestionInteractor
import com.vegdev.vegacademy.presenter.recipes.dialogs.RecipeSuggestionPresenter
import com.vegdev.vegacademy.view.main.MainView
import kotlinx.android.synthetic.main.fragment_recipe_dialog_add.*

class RecipeSuggestionDialogFragment : DialogFragment(), RecipeSuggestionView {

    private var presenter: RecipeSuggestionPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_dialog_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accept.setOnClickListener {
            val recipe = Recipe(
                title_edtxt.editableText.toString(),
                image_edtxt.editableText.toString(),
                ingredients_edtxt.editableText.toString(),
                "taste",
                "meal"
            )
            presenter?.suggestRecipe(recipe)
        }
        cancel.setOnClickListener { dialog?.dismiss() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            RecipeSuggestionPresenter(context, this, RecipeSuggestionInteractor())
    }

    override fun onResume() {
        super.onResume()
        presenter?.setLayoutParams(dialog)
    }

    override fun setLayoutParams(params: WindowManager.LayoutParams?) {
        dialog?.window?.attributes = params
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }
}