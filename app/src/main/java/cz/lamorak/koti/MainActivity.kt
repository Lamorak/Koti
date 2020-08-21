package cz.lamorak.koti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.lamorak.koti.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view_pager.apply {
            adapter = SectionsPagerAdapter(context, supportFragmentManager)
            tabs.setupWithViewPager(this)
        }
    }
}