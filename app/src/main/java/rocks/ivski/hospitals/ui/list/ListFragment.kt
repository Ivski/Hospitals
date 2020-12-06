package rocks.ivski.hospitals.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar.toolbar)

        binding.adapter = adapter

        viewModel.getHospitals().observe(viewLifecycleOwner, {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    it.data?.let { items -> adapter.addItems(items) }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    // TODO: 12/6/20 show error
                }
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onHospitalSelected(hospital: Hospital) {
        findNavController().navigate(R.id.detailsFragment)
    }
}