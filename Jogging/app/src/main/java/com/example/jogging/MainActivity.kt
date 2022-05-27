package com.example.jogging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.jogging.models.Jogging
import com.google.android.material.tabs.TabLayout

private class SectionsPageAdapter : FragmentPagerAdapter() {
    override fun getCount(): Int {

    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            else -> HomeFragment()
        }
    }
}

class MainActivity : AppCompatActivity(), JoggingListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val pager: ViewPager = findViewById(R.id.pager)

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(pager)

    }

    override fun itemClicked(id: Long) {
        val fragmentContainer = findViewById<View>(R.id.fragment_container)
        if(fragmentContainer != null){
            val detailsFrag = JoggingDetailsFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            detailsFrag.setJoggingId(Jogging.getJoggingList()[id.toInt()].id)
            ft.replace(R.id.fragment_container, detailsFrag)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }else {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_JOGGING_ID, id.toInt())
            }
            startActivity(intent)
        }
    }
}