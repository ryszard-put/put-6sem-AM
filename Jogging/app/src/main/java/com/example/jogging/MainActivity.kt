package com.example.jogging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.example.jogging.models.Jogging

class MainActivity : AppCompatActivity(), JoggingListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
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