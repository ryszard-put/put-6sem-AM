package com.example.jogging

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.jogging.models.Jogging
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


class JoggingDetailsFragment : Fragment(), OnMapReadyCallback {
    private var joggingId: String = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            joggingId = savedInstanceState.getString("joggingId").toString()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val jogging = Jogging.getJogging(joggingId)
        println(jogging)
        googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(
                    jogging!!.positions.map{LatLng(it.lat, it.lng)}
                )
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(jogging.positions[0].lat, jogging.positions[0].lng), 12F))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jogging_details, container, false)
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        val stopwatchFAB =  view.findViewById<FloatingActionButton>(R.id.stopwatchFAB)

        stopwatchFAB.setOnClickListener {
            onStopwatchButtonCLicked()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val jogging = Jogging.getJogging(joggingId)
            val title = view.findViewById<TextView>(R.id.textTitle)
            val description = view.findViewById<TextView>(R.id.textDescription)
            title.text = jogging!!.title
            description.text = jogging!!.description
        }
    }

    fun setJoggingId(id: String) {
        joggingId = id
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("joggingId", joggingId)
    }

    fun onStopwatchButtonCLicked() {
        val intent = Intent(context, StopwatchActivity::class.java)
        intent.apply {
            putExtra("JOGGING_ID", joggingId)
        }
        startActivity(intent)
    }
}