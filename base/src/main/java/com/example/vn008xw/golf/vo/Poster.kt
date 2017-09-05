package com.example.vn008xw.carbeat.data.vo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Poster constructor(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float,
    @SerializedName("file_path")
    val filePath: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("iso_639_1")
    val iso: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("width")
    val width: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(aspectRatio)
        parcel.writeString(filePath)
        parcel.writeInt(height)
        parcel.writeString(iso)
        parcel.writeFloat(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Poster> {
        override fun createFromParcel(parcel: Parcel): Poster {
            return Poster(parcel)
        }

        override fun newArray(size: Int): Array<Poster?> {
            return arrayOfNulls(size)
        }
    }
}