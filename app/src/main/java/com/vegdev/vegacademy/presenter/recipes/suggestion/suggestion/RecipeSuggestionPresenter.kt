package com.vegdev.vegacademy.presenter.recipes.suggestion.suggestion

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Ingredient
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.main.dialogs.RecipeSuggestionInteractor
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.ingredients.IngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.steps.StepsAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.suggestion.RecipeSuggestionView
import kotlinx.android.synthetic.main.recipe_suggestion_ingredient.view.*
import kotlinx.android.synthetic.main.recipe_suggestion_step.view.*
import java.io.ByteArrayOutputStream

class RecipeSuggestionPresenter(
    private val context: Context,
    private val fragment: Fragment,
    private val iMainView: MainView,
    private val view: RecipeSuggestionView,
    private val interactor: RecipeSuggestionInteractor
) {

    private val RECIPE_SUGGESTION_PROGRESS = "Enviando receta..."
    private val RECIPE_SUGGESTION_WARNING = "Todos los campos son obligatorios"
    private val RECIPE_SUGGESTION_SUCCESS = "¡Receta enviada!"
    private val RECIPE_SUGGESTION_ERROR = "Error al enviar receta"

    private var ingredientsAdapter = IngredientsAdapter(1)
    private var stepsAdapter = StepsAdapter(1)
    private var recipeKeywords = mutableListOf<String>()

    fun suggestRecipe(recipe: SingleRecipe) {
        if (recipe.title == "" || recipe.desc == "") {
            iMainView.makeToast(RECIPE_SUGGESTION_WARNING)
            return
        }

        iMainView.showProgress()
        iMainView.makeToast(RECIPE_SUGGESTION_PROGRESS)
        view.makeButtonUnclickable()

        // clear keywords property
        recipeKeywords = mutableListOf()

        val ingredients = mutableListOf<Ingredient>()
        for (i in 0 until ingredientsAdapter.size) {
            val ingredientView = view.getIngredientAtPosition(i)
            ingredientView?.let {
                val ingredient = ingredientView.ingredient.text.toString().trim()
                val amount = ingredientView.amount.text.toString().trim()
                ingredients.add(Ingredient(ingredient, amount))

                // create keywords from each ingredient
                recipeKeywords.addAll(createKeywordsListFromWord(ingredient))
            }
        }
        recipe.ing = ingredients

        val steps = mutableListOf<String>()
        for (i in 0 until stepsAdapter.size) {
            val itemView = view.getStepAtPosition(i)
            itemView?.let {
                val step = itemView.single_step.text.toString().trim()
                steps.add(step)
            }
        }
        recipe.steps = steps
        recipe.type = view.getRecipeTypesSpinnerSelectedItem()

        val titleWords = recipe.title.split(" ")
        titleWords.forEach { recipeKeywords.addAll(createKeywordsListFromWord(it)) }
        recipe.keywords = recipeKeywords

        interactor.suggestRecipe(recipe)
            .addOnSuccessListener {
                uploadPictureToFirebaseFirestore(view.getRecipeImage(), recipe.id)
            }.addOnFailureListener {
                iMainView.makeToast(RECIPE_SUGGESTION_ERROR)
            }

    }

    private fun createKeywordsListFromWord(word: String): MutableList<String> {
        val keywords = mutableListOf<String>()
        var currentKeyword = ""
        for (character in word) {
            currentKeyword += character
            keywords.add(currentKeyword)
        }
        return keywords
    }

    private fun uploadPictureToFirebaseFirestore(picture: Bitmap, recipeId: String) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        picture.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val picData = byteArrayOutputStream.toByteArray()
        val uploadTask =
            FirebaseStorage.getInstance().getReference("recipes/recipes/$recipeId")
                .putBytes(picData)

        uploadTask.addOnSuccessListener {
            iMainView.hideProgress()
            view.recipeSentConfirmation()
            iMainView.makeToast(RECIPE_SUGGESTION_SUCCESS)

        }.addOnFailureListener {
            iMainView.makeToast(RECIPE_SUGGESTION_ERROR)
        }
    }

    fun buildIngredientsRecyclerView() {
        view.buildIngredientsRecyclerView(ingredientsAdapter)
    }

    fun addIngredient() {
        ingredientsAdapter.size += 1
        ingredientsAdapter.notifyItemInserted(ingredientsAdapter.size - 1)
    }

    fun buildStepsRecyclerView() {
        view.buildStepsRecyclerView(stepsAdapter)
    }

    fun addStep() {
        stepsAdapter.size += 1

        val position = stepsAdapter.size - 1
        stepsAdapter.notifyItemInserted(stepsAdapter.size - 1)
        val stepView = view.getStepAtPosition(position)
        stepView?.let {

        }
    }

    fun getImage() {
        val intent = createImageChooserIntent()
        fragment.startActivityForResult(intent, 0)
    }

    private fun createImageChooserIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList = mutableListOf<Intent>()

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        intentList = this.addIntentsToList(intentList, pickIntent)
        intentList = this.addIntentsToList(intentList, photoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1), context.getString(
                    R.string.pick_image_intent_text
                )
            )
            chooserIntent?.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray())
        }
        return chooserIntent
    }

    fun onPictureTaken(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            view.setPadding(0)
            data?.let {
                if (it.clipData == null && it.dataString != null) {
                    val uri = it.data
                    uri?.let { view.setRecipeImageUri(uri) }
                } else {
                    val bitmap = it.extras?.get("data") as Bitmap
                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    view.setRecipeImageBitmap(bitmap)
                }
            }
        }
    }

    private fun addIntentsToList(list: MutableList<Intent>, intent: Intent): MutableList<Intent> {
        val resInfoList = context.packageManager.queryIntentActivities(intent, 0)
        for (resInfo in resInfoList) {
            val packageName = resInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.`package` = packageName
            list.add(targetedIntent)
        }
        return list
    }

    fun buildSpinner() {
        val items = listOf("cheeses", "pizzas", "pastas", "desserts")
        val adapter = SpinnerAdapter(context, R.layout.recipes_spinner_item, items)
        view.buildSpinner(adapter)
    }
}