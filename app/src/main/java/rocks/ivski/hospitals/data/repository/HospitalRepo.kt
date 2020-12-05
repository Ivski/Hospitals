package rocks.ivski.hospitals.data.repository

import rocks.ivski.hospitals.data.api.ApiService

class HospitalRepo(private val apiService: ApiService) {

    suspend fun getHospitals() = apiService.getHospitals()
}