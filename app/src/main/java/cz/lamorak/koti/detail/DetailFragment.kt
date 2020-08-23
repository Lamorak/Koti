package cz.lamorak.koti.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import cz.lamorak.koti.Cat
import cz.lamorak.koti.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment(cat: Cat) : Fragment(R.layout.fragment_detail) {

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
    }

    companion object {
        private const val CAT_ID = "cat_id"
        private const val URL = "image_url"
    }
}