package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimData(
    @SerializedName("Result") var result: Boolean? = null,
    @SerializedName("Reason") var reason: String? = null,
    @SerializedName("Claims") var claims: ArrayList<Claims> = arrayListOf()
) : Parcelable