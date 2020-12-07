package rocks.ivski.hospitals.ui.details

import androidx.lifecycle.MutableLiveData
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.ui.base.BaseVM

class DetailsVM : BaseVM() {

    lateinit var hospital: Hospital

    val website = MutableLiveData<String>()
    val phone = MutableLiveData<String>()

    fun loadWebsite() {
        website.postValue(hospital.website)
    }

    fun callPhone() {
        phone.postValue("tel:" + hospital.phone)
    }

}