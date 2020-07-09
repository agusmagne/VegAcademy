package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

data class VideoListFragmentArgs(
  val firestoreCollection: String = "defaultValue"
) : NavArgs {
  fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("firestore_collection", this.firestoreCollection)
    return result
  }

  companion object {
    @JvmStatic
    fun fromBundle(bundle: Bundle): VideoListFragmentArgs {
      bundle.setClassLoader(VideoListFragmentArgs::class.java.classLoader)
      val __firestoreCollection : String?
      if (bundle.containsKey("firestore_collection")) {
        __firestoreCollection = bundle.getString("firestore_collection")
        if (__firestoreCollection == null) {
          throw IllegalArgumentException("Argument \"firestore_collection\" is marked as non-null but was passed a null value.")
        }
      } else {
        __firestoreCollection = "defaultValue"
      }
      return VideoListFragmentArgs(__firestoreCollection)
    }
  }
}
