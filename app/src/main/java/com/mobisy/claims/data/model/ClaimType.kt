package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimType(
    @SerializedName("name") var name: String? = null,
    @SerializedName("id") var id: String? = null
) : Parcelable {

    override fun toString(): String {
        return name!!
    }
}