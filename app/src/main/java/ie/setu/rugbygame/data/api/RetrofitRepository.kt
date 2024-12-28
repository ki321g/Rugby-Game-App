package ie.setu.rugbygame.data.api

import ie.setu.rugbygame.data.model.DonationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitRepository @Inject
  constructor(private val serviceApi: DonationService)  {

    suspend fun getAll(email: String): List<DonationModel>
    {
        return withContext(Dispatchers.IO) {
            val donations = serviceApi.getall(email)
            donations.body() ?: emptyList()
        }
    }

    suspend fun get(email: String,
                    id: String): List<DonationModel>
    {
        return withContext(Dispatchers.IO) {
            val donation = serviceApi.get(email,id)
            donation.body() ?: emptyList()
        }
    }
    suspend fun insert(email: String,
                       donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.post(email, donation)
            wrapper
        }
    }
    suspend fun update(email: String,
                       donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.put(email, donation._id,donation)
            wrapper
        }
    }
    suspend fun delete(email: String, donation: DonationModel) : DonationWrapper
    {
        return withContext(Dispatchers.IO) {
            val wrapper = serviceApi.delete(email, donation._id)
            wrapper
        }
    }
}