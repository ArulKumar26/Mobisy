import com.google.gson.annotations.SerializedName

data class DynamicUiData (
	@SerializedName("logo-url") val logo_url : String?,
	@SerializedName("heading-text") val heading_text : String?,
	@SerializedName("uidata") val uiData : List<UiData>
)