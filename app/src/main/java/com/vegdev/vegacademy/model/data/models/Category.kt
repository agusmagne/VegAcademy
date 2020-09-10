package com.vegdev.vegacademy.model.data.models

import android.os.Parcel
import android.os.Parcelable

class Category(
    var type: String = "",
    var socials: String = "",
    var icon: String = ""
) : Data(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(cat)
        parcel.writeString(src)
        parcel.writeString(title)
        parcel.writeString(socials)
        parcel.writeString(icon)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}