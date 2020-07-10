package com.vegdev.vegacademy.ui.learning

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavArgs
import com.vegdev.vegacademy.models.Category
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

data class VideoListFragmentArgs(
  val category: Category
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  fun toBundle(): Bundle {
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

  companion object {
    @JvmStatic
    fun fromBundle(bundle: Bundle): VideoListFragmentArgs {
      bundle.setClassLoader(VideoListFragmentArgs::class.java.classLoader)
      val __category : Category?
      if (bundle.containsKey("category")) {
        if (Parcelable::class.java.isAssignableFrom(Category::class.java) ||
            Serializable::class.java.isAssignableFrom(Category::class.java)) {
          __category = bundle.get("category") as Category?
        } else {
          throw UnsupportedOperationException(Category::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__category == null) {
          throw IllegalArgumentException("Argument \"category\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"category\" is missing and does not have an android:defaultValue")
      }
      return VideoListFragmentArgs(__category)
    }
  }
}
