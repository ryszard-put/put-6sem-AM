package com.example.jogging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.jogging.api.JoggingApi
import com.example.jogging.models.Jogging
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> CategoryFragment("city")
            2 -> CategoryFragment("offroad")
            else -> HomeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Strona główna"
            1 -> "Trasy miejskie"
            2 -> "Trasy terenowe"
            else -> ""
        }
    }
}

interface Listener {
    fun itemClicked(id: String)
}

class MainActivity : AppCompatActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val pagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val pager: ViewPager = findViewById(R.id.pager)
        pager.adapter = pagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(pager)

    }

    override fun itemClicked(id: String) {
        val fragmentContainer = findViewById<View>(R.id.fragment_container)
        if(fragmentContainer != null){
            val detailsFrag = JoggingDetailsFragment()
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            detailsFrag.setJoggingId(id)
            ft.replace(R.id.fragment_container, detailsFrag)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }else {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_JOGGING_ID, id)
            }
            startActivity(intent)
        }
    }
}