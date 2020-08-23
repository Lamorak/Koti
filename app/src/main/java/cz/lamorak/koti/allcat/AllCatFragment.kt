package cz.lamorak.koti.allcat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.detail.DetailFragment
import cz.lamorak.koti.extensions.setVisible
import kotlinx.android.synthetic.main.fragment_allcat.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCatFragment: Fragment(R.layout.fragment_allcat) {

    private val viewModel by viewModel<AllCatViewModel>()
    private val catAdapter by lazy { AllCatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        lifecycleScope.launch {
            viewModel.getAllCats().collect {
                catAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allcat_recycler.apply {
            val columnCount = resources.getInteger(R.integer.column_count)
            layoutManager = GridLayoutManager(requireContext(), columnCount)
            adapter = catAdapter
        }

        allcar_refresh.setOnRefreshListener {
            catAdapter.refresh()
        }

        error.setOnClickListener {
            catAdapter.retry()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        catAdapter.selectedCats().observe(viewLifecycleOwner) {
            if (it != null) showCatDetail(it)
        }

        catAdapter.addLoadStateListener {
            allcar_refresh.isRefreshing = it.refresh is LoadState.Loading
            error.setVisible(it.refresh is LoadState.Error)
        }
    }

    private fun showCatDetail(cat: Cat) {
        parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailFragment(cat))
                .addToBackStack("detail")
                .commit()
    }
}