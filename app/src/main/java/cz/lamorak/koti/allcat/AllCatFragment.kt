package cz.lamorak.koti.allcat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_allcat.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCatFragment: Fragment(R.layout.fragment_allcat) {

    private val viewModel by viewModel<AllCatViewModel>()
    private val catAdapter by lazy { AllCatAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allcat_recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = catAdapter
        }

        lifecycleScope.launch {
            viewModel.getAllCats().collect {
                catAdapter.submitData(it)
            }
        }

        catAdapter.selectedCats().observe(this) {
            showCatDetail(it)
        }
    }

    private fun showCatDetail(cat: Cat) {
        parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment(cat))
                .addToBackStack("detail")
                .commit()
    }
}