package com.vegdev.vegacademy.view.main.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.main.diaglos.RecipeSuggestionPresenter
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.main.IMainView
import kotlinx.android.synthetic.main.fragment_recipe_dialog_add.*

class RecipeSuggestionView : DialogFragment() {

    private val COMPLETE_ALL_FIELDS_WARNING = "Todos los campos son obligatorios"

    private val layoutUtils = LayoutUtils()
    private var iMainView: IMainView? = null
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
            val name = title_edtxt.editableText.toString()
            val image = image_edtxt.editableText.toString()
            val ingredients = ingredients_edtxt.editableText.toString()
            val taste = ""
            val meal = ""
            if (name != "" && image != "") {
                presenter?.suggestRecipe(name, image, ingredients, taste, meal)
                dialog?.dismiss()
            } else {
                iMainView?.makeToast(COMPLETE_ALL_FIELDS_WARNING)
            }
        }
        cancel.setOnClickListener { dialog?.dismiss() }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) {
            iMainView = context
            presenter = RecipeSuggestionPresenter(context, context)
        }
    }

    override fun onResume() {
        super.onResume()

        // set height and width
        val width = resources.displayMetrics.widthPixels - layoutUtils.toPx(32)
        val height = resources.displayMetrics.heightPixels - layoutUtils.toPx(380)
        val params = dialog?.window?.attributes
        params?.width = width
        params?.height = height
        dialog?.window?.attributes = params
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }
}