package com.mobisy.claims.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClaimTypeDetail(
    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("claimtype_id") var claimtypeId: String? = null,
    @SerializedName("Claimfield") var claimfield: ClaimField?
) : Parcelable