package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimField(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("required") var required: String? = null,
    @SerializedName("isdependant") var isdependant: String? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("modified") var modified: String? = null,
    @SerializedName("Claimfieldoption") var claimfieldoption: ArrayList<ClaimFieldOption> = arrayListOf(),
    @SerializedName("index") var index: Int = -1
) : Parcelable