package cz.lamorak.koti.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import cz.lamorak.koti.databinding.FragmentDetailBinding
import cz.lamorak.koti.favourites.model.FavouriteCat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant

class DetailFragment(cat: Cat) : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.catImage.apply {
            load(imageUrl)
            setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        binding.favourite.setOnClickListener {
            it.isEnabled = false
            if (it.isSelected) viewModel.removeFromFavourites(catId) else addCatToFavourites()
        }

        viewModel.isCatFavourite(catId).observe(viewLifecycleOwner) {
            binding.favourite.apply {
                isSelected = it
                isEnabled = true
            }
        }

        binding.favourite.updateLayoutParams<MarginLayoutParams> {
            val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
                .getInsets(navigationBars())
            val bottomInset = insets.bottom
            updateMargins(bottom = bottomMargin + bottomInset)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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