package rocks.ivski.hospitals.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.data.repository.HospitalRepo
import rocks.ivski.hospitals.ui.base.BaseVM
import rocks.ivski.hospitals.utils.ApiResult
import rocks.ivski.hospitals.utils.NETWORK_ERROR
import rocks.ivski.hospitals.utils.Network
import java.util.*

class ListVM(
    private val repo: HospitalRepo,
    private val helper: Network
) : BaseVM() {

    private val data = MutableLiveData<ApiResult<List<Hospital>>>()
    val filteredResults = MutableLiveData<List<Hospital>>()

    fun getHospitals(): LiveData<ApiResult<List<Hospital>>> {
        if (helper.isConnected()) {
            viewModelScope.launch {
                data.postValue(ApiResult.loading(null))
                repo.getHospitals().let {
                    if (it.isSuccessful) {
                        data.postValue(ApiResult.success(it.body()))
                    } else {
                        data.postValue(ApiResult.error(it.errorBody().toString(), null))
                    }
                }
            }
        } else {
            data.postValue(ApiResult.error(NETWORK_ERROR, null))
        }
        return data
    }

    fun filterByName(startsWith: String): List<Hospital> {
        val result = repo.getData().filter {
            it.organizationName.toLowerCase(Locale.ROOT)
                .startsWith(startsWith.toLowerCase(Locale.ROOT))
        }
        filteredResults.postValue(result)
        return result
    }
}