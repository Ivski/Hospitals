package rocks.ivski.hospitals.ui.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.android.viewmodel.ext.android.viewModel
import rocks.ivski.hospitals.R
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.databinding.FragmentListBinding
import rocks.ivski.hospitals.ui.base.BaseFragment
import rocks.ivski.hospitals.utils.ApiStatus

class ListFragment : BaseFragment<FragmentListBinding, ListVM>(), HospitalSelectionListener {

    override val layoutId = R.layout.fragment_list

    override val viewModel: ListVM by viewModel()

    private val adapter: HospitalAdapter = HospitalAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = adapter

        viewModel.getHospitals().observe(viewLifecycleOwner, {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    it.data?.let { items -> adapter.addItems(items) }
                    adapter.notifyDataSetChanged()
                }
                ApiStatus.ERROR -> {
                }
                ApiStatus.LOADING -> {
                }
            }
        })
    }

    override fun onHospitalSelected(hospital: Hospital) {
        findNavController().navigate(R.id.detailsFragment)
    }
}