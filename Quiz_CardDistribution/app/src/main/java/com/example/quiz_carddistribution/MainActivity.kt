package com.example.quiz_carddistribution

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Declare UI elements
    private lateinit var playerNameInput: EditText
    private lateinit var modeBig: RadioButton
    private lateinit var playerCard: TextView
    private lateinit var computerCard: TextView
    private lateinit var resultTextView: TextView

    // Deck of cards
    private val deck = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        playerNameInput = findViewById(R.id.playerNameInput)
        modeBig = findViewById(R.id.modeBig)
        playerCard = findViewById(R.id.playerCard)
        computerCard = findViewById(R.id.computerCard)
        resultTextView = findViewById(R.id.resultTextView)
        val startButton = findViewById<Button>(R.id.startButton)

        // Initialize the deck of cards
        initializeDeck()

        // Start game when button is clicked
        startButton.setOnClickListener {
            startGame()
        }
    }

    // Initialize deck with 52 cards
    private fun initializeDeck() {
        val suits = listOf("♠", "♥", "♦", "♣") // Suits ordered by priority
        val values = listOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A") // Values
        for (suit in suits) {
            for (value in values) {
                deck.add("$suit$value") // Add card like "♠A", "♣10"
            }
        }
    }

    // Start the game
    private fun startGame() {
        // Get player's name
        val playerName = playerNameInput.text.toString()
        if (playerName.isEmpty()) {
            resultTextView.text = "請輸入玩家姓名"
            return
        }

        // Make sure there are enough cards left
        if (deck.size < 2) {
            resultTextView.text = "牌堆已空，無法再抽牌！"
            return
        }

        // Draw player and computer cards
        val playerCardValue = drawCard()
        val computerCardValue = drawCard()

        // Display the cards
        playerCard.text = "玩家的牌: $playerCardValue"
        computerCard.text = "(電腦)莊家的牌: $computerCardValue"

        // Determine winner based on the game mode
        val winner = if (modeBig.isChecked) {
            determineBigWinner(playerCardValue, computerCardValue, playerName)
        } else {
            determineSmallWinner(playerCardValue, computerCardValue, playerName)
        }

        // Show the result
        resultTextView.text = "勝利者: $winner"
    }

    // Draw a card from the deck
    private fun drawCard(): String {
        val randomIndex = Random.nextInt(deck.size)
        return deck.removeAt(randomIndex) // Remove the drawn card
    }

    // Determine winner for "比大" mode
    private fun determineBigWinner(playerCard: String, computerCard: String, playerName: String): String {
        val playerRank = getCardRank(playerCard)
        val computerRank = getCardRank(computerCard)

        return when {
            playerRank > computerRank -> playerName // Player wins
            playerRank < computerRank -> "電腦"    // Computer wins
            else -> compareSuit(playerCard, computerCard, playerName) // Compare suits if ranks are equal
        }
    }

    // Determine winner for "比小" mode
    private fun determineSmallWinner(playerCard: String, computerCard: String, playerName: String): String {
        val playerRank = getCardRank(playerCard)
        val computerRank = getCardRank(computerCard)

        return when {
            playerRank < computerRank -> playerName // Player wins in "small" mode
            playerRank > computerRank -> "電腦"    // Computer wins
            else -> compareSuit(playerCard, computerCard, playerName) // Compare suits if ranks are equal
        }
    }

    // Compare card suits to break ties (♠ > ♥ > ♣ > ♦)
    private fun compareSuit(playerCard: String, computerCard: String, playerName: String): String {
        val suitOrder = listOf("♠", "♥", "♣", "♦") // Suit priority order
        val playerSuit = playerCard[0].toString() // Get suit of player's card
        val computerSuit = computerCard[0].toString() // Get suit of computer's card

        return if (suitOrder.indexOf(playerSuit) < suitOrder.indexOf(computerSuit)) {
            playerName
        } else {
            "電腦"
        }
    }

    // Get card rank (convert card face to rank value)
    private fun getCardRank(card: String): Int {
        val value = card.drop(1) // Remove the suit, get only value part
        return when (value) {
            "A" -> 1
            "K" -> 13
            "Q" -> 12
            "J" -> 11
            else -> value.toInt()
        }
    }
}
