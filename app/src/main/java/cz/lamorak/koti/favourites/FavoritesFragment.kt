package cz.lamorak.koti.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import cz.lamorak.koti.R
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {

    private val viewModel by viewModel<FavouritesViewModel>()
    private val catAdapter by lazy { FavouritesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favourite_cat_recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = catAdapter
        }

        viewModel.getFavouriteCats().observe(this) {
            catAdapter.submitList(it)
        }
    }
}