package com.example.numberguesser

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var gameData = GameData()
    var randomizedNumber = Random.nextInt(0,20)
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readScoreFromMemory()
        builder = AlertDialog.Builder(this@MainActivity)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val restartButton = findViewById<Button>(R.id.restartButton)
        val newGameButton = findViewById<Button>(R.id.newGameButton)
        val scoreView = findViewById<TextView>(R.id.scoreCountView)
        val guessCountVIew = findViewById<TextView>(R.id.guessCountView)
        val numberInput = findViewById<TextInputLayout>(R.id.numberInput)

        scoreView.text = getString(R.string.score_count, gameData.score)
        guessCountVIew.text = getString(R.string.guess_count, gameData.guess_count)

        restartButton.setOnClickListener() {
            gameData.resetGuessCount()
            gameData.resetScore()
            saveScoreToMemory()
            randomizedNumber = Random.nextInt(0,20)
            scoreView.text = getString(R.string.score_count, gameData.score)
            guessCountVIew.text = getString(R.string.guess_count, gameData.guess_count)
            showToast("Zresetowano stan gry")
        }

        newGameButton.setOnClickListener() {
            gameData.resetGuessCount()
            randomizedNumber = Random.nextInt(0, 20)
            guessCountVIew.text = getString(R.string.guess_count, gameData.guess_count)
            showToast("Wylosowano nową liczbę")
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
                    saveScoreToMemory()
                } else if (randomizedNumber == guessedNumber){
                    val score = determineScore()
                    builder.setTitle("BRAWO")
                    builder.setMessage("Zgadłeś liczbę za ${gameData.guess_count} razem")
                    builder.setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    gameData.resetGuessCount()
                    gameData.addToScore(score)
                    saveScoreToMemory()
                    randomizedNumber = Random.nextInt(0,20)
                } else if (randomizedNumber < guessedNumber){
                    showToast("Podana liczba jest większa od wylosowanej")
                } else if (randomizedNumber > guessedNumber) {
                    showToast("Podana liczba jest mniejsza od wylosowanej")
                }
            }
            guessedNumberText?.clear()
            scoreView.text = getString(R.string.score_count, gameData.score)
            guessCountVIew.text = getString(R.string.guess_count, gameData.guess_count)
        }
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

    fun saveScoreToMemory(){
        val sharedScore = getSharedPreferences("com.example.numberguesser.shared",0)
        val edit = sharedScore.edit()
        edit.putInt("score", gameData.score)
        edit.apply()
    }

    fun readScoreFromMemory(){
        val sharedScore = getSharedPreferences("com.example.numberguesser.shared",0)
        gameData.score = sharedScore.getInt("score", 0)
    }
}