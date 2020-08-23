package cz.lamorak.koti.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {

    private val viewModel by viewModel<FavouritesViewModel>()
    private val catAdapter by lazy { FavouritesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favourite_cat_recycler.apply {
            val columnCount = resources.getInteger(R.integer.column_count)
            layoutManager = GridLayoutManager(requireContext(), columnCount)
            adapter = catAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getFavouriteCats().observe(viewLifecycleOwner) {
            catAdapter.submitList(it)
        }

        catAdapter.selectedCats().observe(viewLifecycleOwner) {
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