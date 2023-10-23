package com.example.ayssoft.model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    val createdAt: String,
    val name: String,
    val image: String,
    val price: Int,
    val description: String,
    val model: String,
    val brand: String,
    val id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeInt(price)
        parcel.writeString(description)
        parcel.writeString(model)
        parcel.writeString(brand)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}