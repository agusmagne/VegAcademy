package com.vegdev.vegacademy.model.data.models.recipes

import android.os.Parcel
import android.os.Parcelable

class SingleRecipe(
    var title: String = "",
    var desc: String = "",
    var steps: MutableList<String> = mutableListOf(),
    var ing: MutableList<Ingredient> = mutableListOf(),
    var type: String = "",
    var likes: Int = 0,
    var id: String = "",
    var keywords: MutableList<String> = mutableListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        mutableListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        },
        mutableListOf<Ingredient>().apply {
            parcel.readList(this, Ingredient::class.java.classLoader)
        },
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        mutableListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeList(steps)
        parcel.writeList(ing)
        parcel.writeString(type)
        parcel.writeInt(likes)
        parcel.writeString(id)
        parcel.writeList(keywords)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SingleRecipe> {
        override fun createFromParcel(parcel: Parcel): SingleRecipe {
            return SingleRecipe(parcel)
        }

        override fun newArray(size: Int): Array<SingleRecipe?> {
            return arrayOfNulls(size)
        }
    }
}

