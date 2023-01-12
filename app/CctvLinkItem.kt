
import com.google.gson.annotations.SerializedName

data class CctvLinkItem(
    @SerializedName("arguments")
    val arguments: List<String>,
    @SerializedName("image")
    val image: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("title")
    val title: String
)