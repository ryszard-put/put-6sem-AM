package com.example.numberguesser.activities.mainactivity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.numberguesser.DB.Contract.Contract
import com.example.numberguesser.GameData
import com.example.numberguesser.R
import com.example.numberguesser.activities.loginactivity.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var gameData = GameData()
    var randomizedNumber = Random.nextInt(0,20)
    private lateinit var builder: AlertDialog.Builder
    lateinit var guessButton : Button
    lateinit var scoreboardButton : Button
    lateinit var newGameButton : Button
    lateinit var logoutButton : Button
    lateinit var scoreView : TextView
    lateinit var guessCountView : TextView
    lateinit var numberInput : TextInputLayout
    lateinit var dbHelper : Contract.UserDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        builder = AlertDialog.Builder(this@MainActivity)
        guessButton = findViewById<Button>(R.id.guessButton)
        scoreboardButton = findViewById<Button>(R.id.buttonScoreboard)
        newGameButton = findViewById<Button>(R.id.newGameButton)
        logoutButton = findViewById(R.id.buttonLogout)
        scoreView = findViewById<TextView>(R.id.scoreCountView)
        guessCountView = findViewById<TextView>(R.id.guessCountView)
        numberInput = findViewById<TextInputLayout>(R.id.numberInput)

        dbHelper = Contract.UserDbHelper(applicationContext)
        val currentUsername = readCurrentUserFromMemory()
        if (currentUsername != null) {
            val user = dbHelper.getUserByUsername(currentUsername)
            gameData.score = user.currentScore
        }

        scoreView.text = getString(R.string.score_count, gameData.score)
        guessCountView.text = getString(R.string.guess_count, gameData.guess_count)

        scoreboardButton.setOnClickListener() {
//            val intent = Intent(this, ScoreboardActivity::class.java)
//            startActivity(intent)
        }

        newGameButton.setOnClickListener() {
            gameData.resetGuessCount()
            randomizedNumber = Random.nextInt(0, 20)
            guessCountView.text = getString(R.string.guess_count, gameData.guess_count)
            showToast("Wylosowano nową liczbę")
        }

        logoutButton.setOnClickListener() {
            saveCurrentUserToMemory("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        guessButton.setOnClickListener() {
            val guessedNumberText = numberInput.editText?.text
            val guessedNumber = guessedNumberText.toString().toIntOrNull()

            if (guessedNumber == null) {
                showToast("Nie podano żadnej liczby")
            } else if (guessedNumber < 0 || guessedNumber > 20) {
                showToast("Podana liczba jest spoza zakresu")
            } else {
                gameData.increaseGuessCount()

                if(gameData.guess_count == 10 && randomizedNumber != guessedNumber) {
                    builder.setTitle("UPS")
                    builder.setMessage("Przegrałeś!")
                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    gameData.resetGuessCount()
                    gameData.resetScore()
                    saveScoreToDB()
                } else if (randomizedNumber == guessedNumber){
                    val score = determineScore()
                    builder.setTitle("BRAWO")
                    builder.setMessage("Zgadłeś liczbę za ${gameData.guess_count} razem")
                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    gameData.resetGuessCount()
                    gameData.addToScore(score)
                    saveScoreToDB()
                    randomizedNumber = Random.nextInt(0,20)
                } else if (randomizedNumber < guessedNumber){
                    showToast("Podana liczba jest większa od wylosowanej")
                } else if (randomizedNumber > guessedNumber) {
                    showToast("Podana liczba jest mniejsza od wylosowanej")
                }
            }
            guessedNumberText?.clear()
            scoreView.text = getString(R.string.score_count, gameData.score)
            guessCountView.text = getString(R.string.guess_count, gameData.guess_count)
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun determineScore(): Int {
        return when (gameData.guess_count) {
            1 -> 5
            in 2..4 -> 3
            in 5..6 -> 2
            in 7..10 -> 1
            else -> 0
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun saveScoreToDB(){
        dbHelper.updateUserScore(readCurrentUserFromMemory()!!, gameData.score)
    }

    private fun saveCurrentUserToMemory(username: String){
        val shared = getSharedPreferences("com.example.numberguesser.shared",0)
        val edit = shared.edit()
        edit.putString("current_user", username)
        edit.apply()
    }

    private fun readCurrentUserFromMemory(): String? {
        val shared = getSharedPreferences("com.example.numberguesser.shared",0)
        return shared.getString("current_user", "")
    }

    private fun saveScoreToMemory(){
        val sharedScore = getSharedPreferences("com.example.numberguesser.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", gameData.score)
        edit.apply()
    }

    private fun readScoreFromMemory(){
        val sharedScore = getSharedPreferences("com.example.numberguesser.shared",0)
        gameData.score = sharedScore.getInt("score", 0)
    }
}