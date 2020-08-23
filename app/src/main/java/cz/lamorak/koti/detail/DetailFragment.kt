package cz.lamorak.koti.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import cz.lamorak.koti.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment(urlArg: String): Fragment(R.layout.fragment_detail) {

    private lateinit var imageUrl: String

    init {
        arguments = bundleOf(URL to urlArg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        private const val URL = "image_url"
    }
}