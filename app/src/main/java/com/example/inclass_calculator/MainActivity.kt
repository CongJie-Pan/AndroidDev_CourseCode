package com.example.inclass_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Variables to reference the UI components
    private lateinit var displayPrimary: TextView
    private lateinit var displaySecondary: TextView

    // Variables for operator and calculation logic
    private var operator: String? = null
    private var firstValue: Double? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding UI elements to Kotlin variables
        displayPrimary = findViewById(R.id.displayPrimary)
        displaySecondary = findViewById(R.id.displaySecondary)

        // Binding buttons for numbers and operations
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonDot = findViewById<Button>(R.id.buttonDot)
        val buttonSqrt = findViewById<Button>(R.id.buttonSqrt)
        val buttonSquare = findViewById<Button>(R.id.buttonSquare)
        val buttonBackspace = findViewById<Button>(R.id.buttonBackspace)

        // Number button click listeners (0 to 9)
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for ((i, button) in numberButtons.withIndex()) {
            button.setOnClickListener { appendNumber(i.toString()) }
        }

        // Operation button click listeners (Add, Subtract, Multiply, Divide)
        buttonAdd.setOnClickListener { setOperator("+") }
        buttonSubtract.setOnClickListener { setOperator("-") }
        buttonMultiply.setOnClickListener { setOperator("×") }
        buttonDivide.setOnClickListener { setOperator("÷") }

        // Clear button to reset the calculator
        buttonClear.setOnClickListener { clearCalculator() }

        // Equals button to calculate the result
        buttonEquals.setOnClickListener { calculateResult() }

        // Dot button for decimal numbers
        buttonDot.setOnClickListener { appendNumber(".") }

        // Square root button to compute the square root
        buttonSqrt.setOnClickListener { computeSquareRoot() }

        // Square button to compute the square of the current number
        buttonSquare.setOnClickListener { computeSquare() }

        // Backspace button to remove the last digit
        buttonBackspace.setOnClickListener { removeLastDigit() }
    }

    // Function to append a number to the current input
    private fun appendNumber(number: String) {
        if (isNewOperation) {
            displayPrimary.text = number
            isNewOperation = false
        } else {
            displayPrimary.append(number)
        }
    }

    // Function to set the operator for the operation (e.g., +, -, ×, ÷)
    private fun setOperator(op: String) {
        firstValue = displayPrimary.text.toString().toDoubleOrNull()
        operator = op
        isNewOperation = true
        displaySecondary.text = "${displayPrimary.text} $operator"
    }

    // Function to calculate and display the result
    private fun calculateResult() {
        val secondValue = displayPrimary.text.toString().toDoubleOrNull()
        if (firstValue != null && secondValue != null && operator != null) {
            val result = when (operator) {
                "+" -> firstValue!! + secondValue
                "-" -> firstValue!! - secondValue
                "×" -> firstValue!! * secondValue
                "÷" -> firstValue!! / secondValue
                else -> 0.0
            }
            displayPrimary.text = result.toString()
            displaySecondary.text = ""
            isNewOperation = true
        }
    }

    // Function to clear all inputs and reset the calculator
    private fun clearCalculator() {
        displayPrimary.text = "0"
        displaySecondary.text = ""
        firstValue = null
        operator = null
        isNewOperation = true
    }

    // Function to compute the square root of the current value
    private fun computeSquareRoot() {
        val value = displayPrimary.text.toString().toDoubleOrNull()
        if (value != null) {
            displayPrimary.text = Math.sqrt(value).toString()
            isNewOperation = true
        }
    }

    // Function to compute the square of the current value
    private fun computeSquare() {
        val value = displayPrimary.text.toString().toDoubleOrNull()
        if (value != null) {
            displayPrimary.text = (value * value).toString()
            isNewOperation = true
        }
    }

    // Function to remove the last digit of the current input
    private fun removeLastDigit() {
        val text = displayPrimary.text.toString()
        if (text.isNotEmpty()) {
            displayPrimary.text = text.dropLast(1)
            if (displayPrimary.text.isEmpty()) {
                displayPrimary.text = "0"
            }
        }
    }
}