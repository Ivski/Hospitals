package rocks.ivski.hospitals.ui.list

import android.os.Bundle
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import rocks.ivski.hospitals.R
import rocks.ivski.hospitals.databinding.FragmentListBinding
import rocks.ivski.hospitals.ui.base.BaseFragment

class ListFragment : BaseFragment<FragmentListBinding, ListVM>() {

    override val layoutId = R.layout.fragment_list

    override val viewModel: ListVM by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}