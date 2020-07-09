package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import androidx.navigation.NavDirections
import com.vegdev.vegacademy.R
import kotlin.Int
import kotlin.String

class LearningFragmentDirections private constructor() {
  private data class ActionVideo(
    val firestoreCollection: String = "defaultValue"
  ) : NavDirections {
    override fun getActionId(): Int = R.id.action_video

    override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("firestore_collection", this.firestoreCollection)
      return result
    }
  }

  companion object {
    fun actionVideo(firestoreCollection: String = "defaultValue"): NavDirections =
        ActionVideo(firestoreCollection)
  }
}
