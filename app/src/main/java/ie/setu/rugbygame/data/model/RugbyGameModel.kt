package ie.setu.rugbygame.data.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date


@Entity
data class RugbyGameModel(
    @DocumentId val _id: String = "N/A",
    var homeTeam: String = "homeTeam",
    var homeTries: Int = 0,
    var homeConversions: Int = 0,
    var homePenalties: Int = 0,
    var homeDropGoals: Int = 0,
    var awayTeam: String = "awayTeam",
    var awayTries: Int = 0,
    var awayConversions: Int = 0,
    var awayPenalties: Int = 0,
    var awayDropGoals: Int = 0,
    val dateGame: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com",
    var imageUri: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)

val fakeGames = List(30) { i ->
    RugbyGameModel(
        _id = "12345" + i,
        "HomeTeam$i",
        i.toInt(),
        i.toInt(),
        i.toInt(),
        i.toInt(),
        "AwayTeam$i",
        i.toInt(),
        i.toInt(),
        i.toInt(),
        i.toInt(),
        Date(),
        Date()
    )
}
