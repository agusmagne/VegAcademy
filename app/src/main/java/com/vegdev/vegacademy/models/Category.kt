package com.vegdev.vegacademy.models

import android.os.Parcel
import android.os.Parcelable

class Category(
    var categoryType: String?,
    var categoryCollection: String?,
    var categoryImage: Int?,
    var categoryTitle: String?,
    var categoryInstagram: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(categoryType)
        parcel.writeString(categoryCollection)
        parcel.writeInt(categoryImage!!)
        parcel.writeString(categoryTitle)
        parcel.writeString(categoryInstagram)

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