package cz.lamorak.koti.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.time.Instant

class DetailFragment(cat: Cat) : Fragment(R.layout.fragment_detail) {

    private val favouritesDao by inject<FavouritesDao>()
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
            favourite.hide()
            addCatToFavourites()
        }
    }

    private fun addCatToFavourites() {
        lifecycleScope.launch(Dispatchers.IO) {
            val cat = FavouriteCat(catId, imageUrl, Instant.now())
            favouritesDao.insertFavoutite(cat)
            showSuccessMessage()
        }
    }

    fun showSuccessMessage() {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(requireContext(), "Cat added to favourites!", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    companion object {
        private const val CAT_ID = "cat_id"
        private const val URL = "image_url"
    }
}