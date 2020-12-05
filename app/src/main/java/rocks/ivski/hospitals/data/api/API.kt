package rocks.ivski.hospitals.data.api

import retrofit2.Response
import retrofit2.http.GET
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.utils.ENDPOINT_HOSPITALS

interface API {

    @GET(ENDPOINT_HOSPITALS)
    suspend fun getHospitals(): Response<List<Hospital>>
}