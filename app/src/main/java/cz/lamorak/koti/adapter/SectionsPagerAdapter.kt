package cz.lamorak.koti.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import cz.lamorak.koti.allcat.AllCatFragment
import cz.lamorak.koti.favourites.FavoritesFragment
import cz.lamorak.koti.R

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles by lazy { context.resources.getStringArray(R.array.tab_titles) }

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> AllCatFragment()
        else -> FavoritesFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]

    override fun getCount() = 2
}