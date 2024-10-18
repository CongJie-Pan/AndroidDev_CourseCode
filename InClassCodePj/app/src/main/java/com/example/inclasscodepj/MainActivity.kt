package com.example.inclasscodepj

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare views as class-level variables for easy access
    private lateinit var resultTextView: TextView
    private lateinit var userChoiceTextView: TextView
    private lateinit var computerChoiceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views by IDs
        resultTextView = findViewById(R.id.resultTextView)
        userChoiceTextView = findViewById(R.id.userChoiceTextView)
        computerChoiceTextView = findViewById(R.id.computerChoiceTextView)
        val scissorsButton = findViewById<Button>(R.id.scissorsButton)
        val rockButton = findViewById<Button>(R.id.rockButton)
        val paperButton = findViewById<Button>(R.id.paperButton)
        val playAgainButton = findViewById<Button>(R.id.button)

        // Set click listeners for buttons
        scissorsButton.setOnClickListener { playGame("剪刀✂️") }
        rockButton.setOnClickListener { playGame("石頭🪨") }
        paperButton.setOnClickListener { playGame("布📜") }
        playAgainButton.setOnClickListener { resetGame() }
    }

    // Function to play the game
    private fun playGame(userChoice: String) {
        // Define possible choices
        val choices = arrayOf("石頭🪨", "布📜", "剪刀✂️")
        // Randomly pick a choice for the computer
        val computerChoice = choices[Random.nextInt(choices.size)]

        // Update the UI with user and computer choices
        userChoiceTextView.text = "你選擇：$userChoice"
        computerChoiceTextView.text = "電腦選擇：$computerChoice"

        // Determine the game result
        val result = when {
            userChoice == computerChoice -> "平手！" // It's a tie
            (userChoice == "石頭🪨" && computerChoice == "剪刀✂️") ||
                    (userChoice == "剪刀✂️" && computerChoice == "布📜") ||
                    (userChoice == "布📜" && computerChoice == "石頭🪨") -> "你贏了！" // User wins
            else -> "你輸了！" // Computer wins
        }

        // Update the result TextView with the outcome
        resultTextView.text = "遊戲結果：$result"
    }

    // Function to reset the game
    private fun resetGame() {
        // Clear the text of each TextView for a new game
        userChoiceTextView.text = "你選擇："
        computerChoiceTextView.text = "電腦選擇："
        resultTextView.text = "遊戲結果："
    }
}
