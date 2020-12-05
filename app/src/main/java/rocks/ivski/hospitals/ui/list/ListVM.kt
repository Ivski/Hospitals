package rocks.ivski.hospitals.ui.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.data.repository.HospitalRepo
import rocks.ivski.hospitals.ui.base.BaseVM
import rocks.ivski.hospitals.utils.ApiResult
import rocks.ivski.hospitals.utils.Network

class ListVM(
    private val repo: HospitalRepo,
    private val helper: Network
) : BaseVM() {

    val hospitals = MutableLiveData<ApiResult<List<Hospital>>>()

    init {
        getHospitals()
    }

    private fun getHospitals() {
        viewModelScope.launch {
            hospitals.postValue(ApiResult.loading(null))
            if (helper.isConnected()) {
                repo.getHospitals().let {
                    if (it.isSuccessful) {
                        hospitals.postValue(ApiResult.success(it.body()))
                    } else {
                        hospitals.postValue(ApiResult.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                hospitals.postValue(ApiResult.error("No internet connection", null))
            }
        }
    }
}