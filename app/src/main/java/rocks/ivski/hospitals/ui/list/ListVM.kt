package rocks.ivski.hospitals.ui.list

import androidx.lifecycle.LiveData
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

    fun getHospitals(): LiveData<ApiResult<List<Hospital>>> {
        val hospitals = MutableLiveData<ApiResult<List<Hospital>>>()
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
        return hospitals
    }
}