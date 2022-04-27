package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Claims(
    @SerializedName("Claimtype") var claimtype: ClaimType? = ClaimType(),
    @SerializedName("Claimtypedetail") var claimtypedetail: ArrayList<ClaimTypeDetail> = arrayListOf()
) : Parcelable