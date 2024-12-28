package ie.setu.rugbygame.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.setu.rugbygame.firebase.auth.AuthRepository
import ie.setu.rugbygame.firebase.database.FirestoreRepository
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth):
            AuthService = AuthRepository(firebaseAuth = auth)

    @Provides
    fun provideFirebaseFirestore()
            : FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirestoreRepository(
        auth: AuthService,
        firebaseFirestore: FirebaseFirestore
    ) : FirestoreService = FirestoreRepository(
        auth = auth,
        firestore = firebaseFirestore
    )

}
