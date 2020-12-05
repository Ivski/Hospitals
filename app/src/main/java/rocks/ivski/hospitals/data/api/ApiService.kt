package rocks.ivski.hospitals.data.api

import retrofit2.Response
import rocks.ivski.hospitals.data.model.Hospital

class ApiService(private val api: API) {

    suspend fun getHospitals(): Response<List<Hospital>> = api.getHospitals()

}