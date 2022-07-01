package cz.lamorak.koti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.lamorak.koti.adapter.SectionsPagerAdapter
import cz.lamorak.koti.databinding.ActivityMainBinding
import cz.lamorak.koti.work.WorkCommander
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val workCommander by inject<WorkCommander>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.viewPager.apply {
            adapter = SectionsPagerAdapter(context, supportFragmentManager)
            binding.tabs.setupWithViewPager(this)
        }

        workCommander.synchronizeFavourites()
    }
}