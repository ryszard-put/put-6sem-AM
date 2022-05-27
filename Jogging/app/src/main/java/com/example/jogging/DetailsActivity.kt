package com.example.jogging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.example.jogging.models.Jogging

class DetailsActivity : AppCompatActivity() {
    companion object {
        val EXTRA_JOGGING_ID: String = "id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val joggingDetailsFragment = supportFragmentManager.findFragmentById(R.id.details_frag) as JoggingDetailsFragment
        val joggingId = intent.extras!!.get(EXTRA_JOGGING_ID)
        joggingDetailsFragment.setJoggingId(Jogging.getJoggingList()[joggingId as Int].id)
    }
}