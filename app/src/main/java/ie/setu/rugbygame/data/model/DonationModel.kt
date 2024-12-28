package ie.setu.rugbygame.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity
data class DonationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @DocumentId val _id: String = "N/A",
    @SerializedName("paymenttype")
    val paymentType: String = "N/A",
    @SerializedName("paymentamount")
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    @SerializedName("datedonated")
    val dateDonated: Date = Date(),
    @SerializedName("datemodified")
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = ""
)

val fakeDonations = List(30) { i ->
    DonationModel(id = 12345 + i,
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date(),
        Date()
    )
}
