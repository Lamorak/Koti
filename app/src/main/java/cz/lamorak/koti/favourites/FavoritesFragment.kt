package cz.lamorak.koti.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.databinding.FragmentFavoritesBinding
import cz.lamorak.koti.detail.DetailFragment
import cz.lamorak.koti.favourites.model.FavouriteCat
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavouritesViewModel>()
    private val catAdapter by lazy { FavouritesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favouriteCatRecycler.apply {
            val columnCount = resources.getInteger(R.integer.column_count)
            layoutManager = GridLayoutManager(requireContext(), columnCount)
            adapter = catAdapter
        }

        viewModel.getFavouriteCats()
            .observe(viewLifecycleOwner) { favouriteCats: List<FavouriteCat> ->
                catAdapter.submitList(favouriteCats)
            }

        catAdapter.selectedCats().observe(viewLifecycleOwner) { selectedCat: Cat ->
            showCatDetail(selectedCat)
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