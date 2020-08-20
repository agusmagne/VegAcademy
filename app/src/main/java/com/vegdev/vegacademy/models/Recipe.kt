package com.vegdev.vegacademy.models

import android.os.Parcel
import android.os.Parcelable

class Recipe(
    var title: String,
    var src: String,
    var ing: String,
    var taste: String,
    var meal: String,
    var likes: Int,
    var id: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    constructor() : this("", "", "", "", "", 0, "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(src)
        parcel.writeString(ing)
        parcel.writeString(taste)
        parcel.writeString(meal)
        parcel.writeInt(likes)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}

class Filter(var title: String, var type: Int)