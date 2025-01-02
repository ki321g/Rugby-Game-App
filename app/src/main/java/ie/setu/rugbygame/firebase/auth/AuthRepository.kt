package ie.setu.rugbygame.firebase.auth

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirebaseSignInResponse
import ie.setu.rugbygame.firebase.services.SignInWithGoogleResponse
import ie.setu.rugbygame.firebase.services.StorageService
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepository
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val storageService: StorageService
) : AuthService {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override val isUserAuthenticatedInFirebase : Boolean
        get() = firebaseAuth.currentUser != null

    override val email: String?
        get() = firebaseAuth.currentUser?.email

    override val customPhotoUri: Uri?
        get() = firebaseAuth.currentUser!!.photoUrl

    override suspend fun authenticateUser(email: String, password: String)
            : FirebaseSignInResponse {
        return try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(email, password).await()
            Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun createUser(name: String, email: String, password: String): FirebaseSignInResponse {
        return try {
            val uri = Uri.parse("android.resource://ie.setu.rugbygame/drawable/profile_temp")
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .setPhotoUri(uploadCustomPhotoUri(uri))
                .build())?.await()
            return Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun authenticateGoogleUser(googleIdToken: String) : FirebaseSignInResponse {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val result = firebaseAuth.signInWithCredential(firebaseCredential).await()
            Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = firebaseAuth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                //   addUserToFirestore()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updatePhoto(uri: Uri) : FirebaseSignInResponse {
        return try {
            currentUser!!.updateProfile(UserProfileChangeRequest
                .Builder()
                .setPhotoUri(uploadCustomPhotoUri(uri))
                .build()).await()
            return Response.Success(currentUser!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

    private suspend fun uploadCustomPhotoUri(uri: Uri) : Uri {
        if (uri.toString().isNotEmpty()) {
            val urlTask = storageService.uploadFile(uri = uri, "images")
            val url = urlTask.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e("task not successful: ${task.exception}")
                }
            }.await()
            return url
        }
        return Uri.EMPTY
    }

}
