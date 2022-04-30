import com.google.gson.annotations.SerializedName

data class UiData (
	@SerializedName("uitype") val uiType : String,
	@SerializedName("value") val value : String,
	@SerializedName("key") val key : String
)