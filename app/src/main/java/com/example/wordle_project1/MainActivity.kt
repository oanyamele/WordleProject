package com.example.wordle_project1

import android.os.Bundle
//import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var submitButton: Button
    private lateinit var resetButton: Button
    private lateinit var guessInput: EditText
    private lateinit var feedback1: TextView
    private lateinit var feedback2: TextView
    private lateinit var feedback3: TextView

    private var wordToGuess: String = ""
    private var guessCount = 0
    private val maxGuesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        guessInput = findViewById(R.id.guessInput)
        submitButton = findViewById(R.id.submitButton)
        resetButton = findViewById(R.id.resetButton)
        feedback1 = findViewById(R.id.feedback1)
        feedback2 = findViewById(R.id.feedback2)
        feedback3 = findViewById(R.id.feedback3)

        submitButton.setOnClickListener {
            handleGuess()
        }

        resetButton.setOnClickListener {
            resetGame()
        }

        resetGame()
    }

    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            result += when {
                guess[i] == wordToGuess[i] -> "0"
                guess[i] in wordToGuess -> "+"
                else -> "X"
            }
        }
        return result
    }

    private fun handleGuess() {
        val guess = guessInput.text.toString().trim().uppercase()

        if (guess.length != 4) {
            Toast.makeText(this, "Please enter a 4-letter word", Toast.LENGTH_SHORT).show()
            return
        }

        val result = checkGuess(guess)
        val feedbackText = "$guess : $result"

        when (guessCount) {
            0 -> feedback1.text = feedbackText
            1 -> feedback2.text = feedbackText
            2 -> feedback3.text = result
        }

        guessCount++

        if (guess == wordToGuess || guessCount >= maxGuesses) {
            submitButton.isEnabled = false
            Toast.makeText(this, "Game Over. The word was: $wordToGuess", Toast.LENGTH_LONG).show()
        }

        guessInput.text.clear()
    }

    private fun resetGame() {
        val wordList = listOf("TREE", "CODE", "PLAY", "JAVA", "FISH", "WORD")
        wordToGuess = wordList.random()
        guessCount = 0
        feedback1.text = ""
        feedback2.text = ""
        feedback3.text = ""
        submitButton.isEnabled = true
        guessInput.text.clear()
        Toast.makeText(this, "New Game!", Toast.LENGTH_SHORT).show()
    }
}