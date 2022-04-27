package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimFieldOption(
    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("belongsto") var belongsto: String? = null,
    @SerializedName("hasmany") var hasmany: String? = null,
    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null,
) : Parcelable {
    override fun toString(): String {
        return name!!
    }
}