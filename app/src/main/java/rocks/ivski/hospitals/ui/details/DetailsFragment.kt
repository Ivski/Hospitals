package rocks.ivski.hospitals.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.koin.android.viewmodel.ext.android.viewModel
import rocks.ivski.hospitals.R
import rocks.ivski.hospitals.databinding.FragmentDetailsBinding
import rocks.ivski.hospitals.ui.base.BaseFragment

class DetailsFragment: BaseFragment<FragmentDetailsBinding, DetailsVM>() {

    override val layoutId = R.layout.fragment_details
    override val viewModel: DetailsVM by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar.toolbar)
    }


}