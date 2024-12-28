package ie.setu.rugbygame.data.api

import ie.setu.rugbygame.data.model.DonationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DonationService {
    @GET(ServiceEndPoints.DONATIONS_ENDPOINT + "/{email}")
    suspend fun getall(
        @Path("email") email: String)
            : Response<List<DonationModel>>

    @GET(ServiceEndPoints.DONATIONS_ENDPOINT + "/{email}" + "/{id}")
    suspend fun get(@Path("email") email: String,
                    @Path("id") id: String): Response<List<DonationModel>>

    @DELETE(ServiceEndPoints.DONATIONS_ENDPOINT + "/{email}" + "/{id}")
    suspend fun delete(@Path("email") email: String,
                       @Path("id") id: String): DonationWrapper

    @POST(ServiceEndPoints.DONATIONS_ENDPOINT + "/{email}")
    suspend fun post(@Path("email") email: String,
                     @Body donation: DonationModel): DonationWrapper

    @PUT(ServiceEndPoints.DONATIONS_ENDPOINT + "/{email}" + "/{id}")
    suspend fun put(@Path("email") email: String,
                    @Path("id") id: String,
                    @Body donation: DonationModel
    ): DonationWrapper
}