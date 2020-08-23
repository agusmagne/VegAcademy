package com.vegdev.vegacademy.data.models

import android.os.Parcel
import android.os.Parcelable

class Category(
    var type: String?,
    var cat: String?,
    var src: String?,
    var title: String?,
    var socials: String?,
    var icon: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    constructor() : this("", "", "", "", "", "")

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