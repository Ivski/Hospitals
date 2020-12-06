package rocks.ivski.hospitals.ui.list

import rocks.ivski.hospitals.data.model.Hospital

interface HospitalSelectionListener {
    fun onHospitalSelected(hospital: Hospital)
}