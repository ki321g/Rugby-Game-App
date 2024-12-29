package ie.setu.rugbygame.data.model

import android.net.Uri
import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class RugbyGameModel(
    @DocumentId val _id: String = "N/A",
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    val dateDonated: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

val fakeGames = List(30) { i ->
    RugbyGameModel(
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date(),
        Date()
    )
}
