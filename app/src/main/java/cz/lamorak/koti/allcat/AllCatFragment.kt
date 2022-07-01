package cz.lamorak.koti.allcat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.*
import androidx.core.view.WindowInsetsCompat.CONSUMED
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.databinding.FragmentAllcatBinding
import cz.lamorak.koti.detail.DetailFragment
import cz.lamorak.koti.extensions.setVisible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllCatFragment : Fragment(R.layout.fragment_allcat) {

    private var _binding: FragmentAllcatBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AllCatViewModel>()
    private val catAdapter by lazy { AllCatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.getAllCats().collect {
                catAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllcatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val insets = windowInsets.getInsets(systemBars())
            if (insets.bottom != 0 && insets.bottom != binding.allcatLoading.marginBottom) {
                binding.allcatLoading.updateLayoutParams<MarginLayoutParams> {
                    bottomMargin = insets.bottom
                }
                binding.allcatRecycler.updatePadding(
                    bottom = insets.bottom
                )
            }
            CONSUMED
        }

        binding.allcatRecycler.apply {
            val columnCount = resources.getInteger(R.integer.column_count)
            layoutManager = GridLayoutManager(requireContext(), columnCount)
            adapter = catAdapter
        }

        binding.allcatRefresh.setOnRefreshListener {
            catAdapter.refresh()
        }

        binding.allcatError.setOnClickListener {
            catAdapter.retry()
        }

        catAdapter.selectedCats().observe(viewLifecycleOwner) {
            if (it != null) showCatDetail(it)
        }

        catAdapter.addLoadStateListener {
            binding.allcatLoading.isVisible = it.append is LoadState.Loading
            binding.allcatRefresh.isRefreshing = it.refresh is LoadState.Loading
            binding.allcatError.setVisible(it.refresh is LoadState.Error)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showCatDetail(cat: Cat) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailFragment(cat))
            .addToBackStack("detail")
            .commit()
    }
}