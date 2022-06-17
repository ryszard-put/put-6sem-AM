package com.example.jogging

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.*


class StopwatchActivity : AppCompatActivity() {
    lateinit var joggingId: String
    private lateinit var stopWatchTextView: TextView
    private lateinit var bestTimeTextView: TextView
    private lateinit var lastTimeTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private val timer = Timer()
    private var timeSeconds: Long = 0
    private var bestTime: Long = 0
    private var lastTime: Long = 0

    private var timerCounting = false
    var PREFERENCES = "prefs"
    var BEST_TIME_KEY = "bestKey"
    var LAST_TIME_KEY = "lastKey"

    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        joggingId = intent.getStringExtra("JOGGING_ID").toString()

        bestTimeTextView = findViewById(R.id.bestTimeTextView)
        lastTimeTextView = findViewById(R.id.lastTimeTextView)
        stopWatchTextView = findViewById(R.id.stopWatchTextView)
        startButton = findViewById(R.id.startStopWatchButton);
        stopButton = findViewById(R.id.stopStopWatchButton)
//
        PREFERENCES += joggingId
        BEST_TIME_KEY += joggingId
        LAST_TIME_KEY += joggingId
        sharedPref =  getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

        bestTime = sharedPref.getLong(BEST_TIME_KEY,0)
        lastTime = sharedPref.getLong(LAST_TIME_KEY,0)

        bestTimeTextView.text = "Najlepszy czas: ${getTimeString(bestTime)}"
        lastTimeTextView.text = "Ostatni czas: ${getTimeString(lastTime)}"

        timer.scheduleAtFixedRate(TimeTask(), 0, 1000)

        startButton.setOnClickListener{startStopWatch()}
        stopButton.setOnClickListener { stopStopWatch() }
        stopButton.isClickable = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                stopStopWatch()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class TimeTask: TimerTask()
    {
        override fun run()
        {
            if(timerCounting)
            {
                timeSeconds += 1
                stopWatchTextView.text = getTimeString(timeSeconds)
            }
        }
    }

    private fun startStopWatch() {
        startButton.isClickable = false
        stopButton.isClickable = true
        timerCounting = true

    }

    private fun stopStopWatch() {
        timerCounting = false
        startButton.isClickable = true
        stopButton.isClickable = false
        stopWatchTextView.text = "00:00:00"
        with(sharedPref.edit()){
            if(timeSeconds > bestTime) putLong(BEST_TIME_KEY, timeSeconds)
            putLong(LAST_TIME_KEY, timeSeconds)
            apply()
        }

        bestTime = sharedPref.getLong(BEST_TIME_KEY,0)
        lastTime = sharedPref.getLong(LAST_TIME_KEY,0)

        bestTimeTextView.text = "Najlepszy czas: ${getTimeString(bestTime)}"
        lastTimeTextView.text = "Ostatni czas: ${getTimeString(lastTime)}"
        timeSeconds = 0
    }

    private fun getTimeString(seconds: Long): String {
        val hours = (seconds/(60*60) % 24)
        val minutes = (seconds/(60) % 60)
        val seconds = (seconds % 60)

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String = String.format("%02d:%02d:%02d", hours, minutes, seconds)
}