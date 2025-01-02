package ie.setu.rugbygame.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.Query
import ie.setu.rugbygame.data.rules.Constants.GAME_COLLECTION
import ie.setu.rugbygame.data.rules.Constants.USER_EMAIL
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.Game
import ie.setu.rugbygame.firebase.services.Games
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Games {

        return firestore.collection(GAME_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String,
                             gameId: String): Game? {
        return firestore.collection(GAME_COLLECTION)
            .document(gameId).get().await().toObject()
    }

    override suspend fun getLatest(email: String): Games {
        return firestore.collection(GAME_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .orderBy("dateGame", Query.Direction.DESCENDING)
            .limit(1)
            .dataObjects()
    }

    override suspend fun insert(email: String, game: Game) {
        val gameWithEmailAndImage =
            game.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(GAME_COLLECTION)
            .add(gameWithEmailAndImage)
            .await()
    }


    override suspend fun update(email: String,
                                game: Game)
    {
        val gameWithModifiedDate =
            game.copy(dateModified = Date())

        firestore.collection(GAME_COLLECTION)
            .document(game._id)
            .set(gameWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                gameId: String)
    {
        firestore.collection(GAME_COLLECTION)
            .document(gameId)
            .delete().await()
    }

    override suspend fun updatePhotoUris(email: String, uri: Uri) {

        firestore.collection(GAME_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Timber.i("FSR Updating ID ${document.id}")
                    firestore.collection(GAME_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }

}