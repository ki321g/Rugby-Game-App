package ie.setu.rugbygame.firebase.database

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.rugbygame.data.rules.Constants.DONATION_COLLECTION
import ie.setu.rugbygame.data.rules.Constants.USER_EMAIL
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.Donation
import ie.setu.rugbygame.firebase.services.Donations
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(email: String): Donations {

        return firestore.collection(DONATION_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .dataObjects()
    }

    override suspend fun get(email: String,
                             donationId: String): Donation? {
        return firestore.collection(DONATION_COLLECTION)
            .document(donationId).get().await().toObject()
    }

    override suspend fun insert(email: String, donation: Donation) {
        val donationWithEmailAndImage =
            donation.copy(
                email = email,
                imageUri = auth.customPhotoUri!!.toString()
            )

        firestore.collection(DONATION_COLLECTION)
            .add(donationWithEmailAndImage)
            .await()
    }


    override suspend fun update(email: String,
                                donation: Donation)
    {
        val donationWithModifiedDate =
            donation.copy(dateModified = Date())

        firestore.collection(DONATION_COLLECTION)
            .document(donation._id)
            .set(donationWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                donationId: String)
    {
        firestore.collection(DONATION_COLLECTION)
            .document(donationId)
            .delete().await()
    }

    override suspend fun updatePhotoUris(email: String, uri: Uri) {

        firestore.collection(DONATION_COLLECTION)
            .whereEqualTo(USER_EMAIL, email)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Timber.i("FSR Updating ID ${document.id}")
                    firestore.collection(DONATION_COLLECTION)
                        .document(document.id)
                        .update("imageUri", uri.toString())
                }
            }
            .addOnFailureListener { exception ->
                Timber.i("Error $exception")
            }
    }

}