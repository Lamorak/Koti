package cz.lamorak.koti.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant

class DetailFragment(cat: Cat) : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModel<DetailViewModel>()
    private lateinit var catId: String
    private lateinit var imageUrl: String

    init {
        arguments = bundleOf(
                CAT_ID to cat.id,
                URL to cat.url
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catId = arguments?.getString(CAT_ID)!!
        imageUrl = arguments?.getString(URL)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cat_image.load(imageUrl)
        cat_image.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        favourite.setOnClickListener {
            favourite.run {
                isEnabled = false
                if (isSelected) viewModel.removeFromFavourites(catId) else addCatToFavourites()
            }
        }

        viewModel.isCatFavourite(catId).observe(this) {
            favourite.isSelected = it
            favourite.isEnabled = true
        }
    }

    private fun addCatToFavourites() {
        val cat = FavouriteCat(catId, imageUrl, Instant.now())
        viewModel.addCatToFavourites(cat)
    }

    companion object {
        private const val CAT_ID = "cat_id"
        private const val URL = "image_url"
    }
}