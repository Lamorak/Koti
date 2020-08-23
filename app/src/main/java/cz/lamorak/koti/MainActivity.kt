package cz.lamorak.koti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.lamorak.koti.adapter.SectionsPagerAdapter
import cz.lamorak.koti.work.WorkCommander
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val workCommander by inject<WorkCommander>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view_pager.apply {
            adapter = SectionsPagerAdapter(context, supportFragmentManager)
            tabs.setupWithViewPager(this)
        }

        workCommander.synchronizeFavourites()
    }
}