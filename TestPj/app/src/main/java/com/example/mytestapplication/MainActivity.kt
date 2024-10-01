package com.example.mytestapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Variable to store the random number
    var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout resource file for the activity
        setContentView(R.layout.activity_main)

        // Generate a random number between 1 and 100
        randomNumber = Random.nextInt(1, 101)

        // Find views by their ID
        val guessInput = findViewById<EditText>(R.id.guessInput)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        // Set an OnClickListener on the button
        guessButton.setOnClickListener {
            // Get the input from the EditText
            val guess = guessInput.text.toString().toIntOrNull()

            // Check if the input is valid
            if (guess != null) {
                // Compare the input number with the random number
                when {
                    guess < randomNumber -> resultText.text = "Too low! Try again."
                    guess > randomNumber -> resultText.text = "Too high! Try again."
                    else -> resultText.text = "Congratulations! You guessed it!"
                }
            } else {
                // If input is invalid, ask the user to enter a number
                resultText.text = "Please enter a valid number."
            }
        }
    }
}