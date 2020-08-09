package com.vegdev.vegacademy.ui.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.IRecipeManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Recipe
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_recipe_dialog_add.*
import java.util.*

class RecipeDialogAddFragment : DialogFragment() {

    private val layoutUtils = LayoutUtils()
    private var iRecipeManager: IRecipeManager? = null

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
            if (name != "" &&
                image != "" &&
                ingredients != ""
            ) {
                layoutUtils.createToast(requireContext(), "Enviando receta")
                iRecipeManager?.uploadRecipe(
                    Recipe(
                        name.toLowerCase(Locale.ROOT),
                        image,
                        ingredients
                    )
                )
                dialog?.dismiss()
            } else {
                layoutUtils.createToast(requireContext(), "Todos los campos son obligatorios")
            }
        }
        cancel.setOnClickListener { dialog?.dismiss() }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IRecipeManager) iRecipeManager = context
    }

    override fun onDetach() {
        super.onDetach()
        iRecipeManager = null
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