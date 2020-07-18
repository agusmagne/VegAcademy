package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Category
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

class LearningFragmentDirections private constructor() {
  private data class ActionVideo(
    val category: Category
  ) : NavDirections {
    override fun getActionId(): Int = R.id.action_video

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Category::class.java)) {
        result.putParcelable("category", this.category as Parcelable)
      } else if (Serializable::class.java.isAssignableFrom(Category::class.java)) {
        result.putSerializable("category", this.category as Serializable)
      } else {
        throw UnsupportedOperationException(Category::class.java.name +
            " must implement Parcelable or Serializable or must be an Enum.")
      }
      return result
    }
  }

  companion object {
    fun actionVideo(category: Category): NavDirections = ActionVideo(category)
  }
}
