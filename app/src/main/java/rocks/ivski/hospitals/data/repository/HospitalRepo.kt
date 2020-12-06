package rocks.ivski.hospitals.data.repository

import retrofit2.Response
import rocks.ivski.hospitals.data.api.ApiService
import rocks.ivski.hospitals.data.model.Hospital
import java.net.HttpURLConnection

class HospitalRepo(private val apiService: ApiService) {

    private var data: List<Hospital> = emptyList()

    suspend fun getHospitals(): Response<List<Hospital>> {
        return if (data.isEmpty()) {
            val result = apiService.getHospitals()
            if (result.isSuccessful) {
                data = result.body()!!
            }
            return result
        } else {
            Response.success(HttpURLConnection.HTTP_OK, data)
        }
    }

    fun getData(): List<Hospital> {
        return data
    }
}