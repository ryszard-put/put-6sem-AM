package com.example.numberguesser.activities.scoreboardactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numberguesser.R

class ScoreboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        messageAdapter = messageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.messageList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)


        for(a in 1..10) {
            val new = message("Nowe Zadanie $a", "as"+Random.nextInt(10000,20000).toString(), "Waszym zadaniem jest zrobie $a")
            messageAdapter.addMessage(new)
        }
    }
}