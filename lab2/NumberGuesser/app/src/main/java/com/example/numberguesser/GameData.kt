package com.example.numberguesser

class GameData {
    var guess_count: Int = 0
    var score: Int = 0

    fun increaseGuessCount() { guess_count++ }

    fun resetGuessCount() { guess_count = 0 }

    fun addToScore(points: Int) { score += points }

    fun resetScore() { score = 0 }
}