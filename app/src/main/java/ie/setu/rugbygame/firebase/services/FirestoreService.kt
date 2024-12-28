package ie.setu.rugbygame.firebase.services

import ie.setu.rugbygame.data.model.DonationModel
import kotlinx.coroutines.flow.Flow

typealias Donation = DonationModel
typealias Donations = Flow<List<Donation>>

interface FirestoreService {

    suspend fun getAll(email: String) : Donations
    suspend fun get(email: String, donationId: String) : Donation?
    suspend fun insert(email: String, donation: Donation)
    suspend fun update(email: String, donation: Donation)
    suspend fun delete(email: String, donationId: String)
}