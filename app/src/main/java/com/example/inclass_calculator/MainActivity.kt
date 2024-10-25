package com.example.inclass_calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // UI elements to display the main and secondary results
    private lateinit var displayPrimary: TextView
    private lateinit var displaySecondary: TextView

    // Variables to store the current operator, first value, and track new operations
    private var operator: String? = null
    private var firstValue: Double? = null
    private var secondValue: Double? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding the UI elements (TextViews and Buttons)
        displayPrimary = findViewById(R.id.displayPrimary)
        displaySecondary = findViewById(R.id.displaySecondary)

        // Number buttons (0 to 9) binding
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

        // Operation buttons binding
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

        // Setting click listeners for number buttons
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for ((i, button) in numberButtons.withIndex()) {
            button.setOnClickListener { appendNumber(i.toString()) }
        }

        // Setting operation button listeners
        buttonAdd.setOnClickListener { handleOperation("+") }
        buttonSubtract.setOnClickListener { handleOperation("-") }
        buttonMultiply.setOnClickListener { handleOperation("×") }
        buttonDivide.setOnClickListener { handleOperation("÷") }

        // Equals button for result calculation
        buttonEquals.setOnClickListener { calculateResult() }

        // Dot button for decimal numbers
        buttonDot.setOnClickListener { appendNumber(".") }

        // Clear button to reset the calculator
        buttonClear.setOnClickListener { clearCalculator() }

        // Square root and square button listeners
        buttonSqrt.setOnClickListener { computeSquareRoot() }
        buttonSquare.setOnClickListener { computeSquare() }

        // Backspace button to remove the last digit
        buttonBackspace.setOnClickListener { removeLastDigit() }
    }

    // Dynamically resize font only if the length of the number exceeds the display size
    private fun adjustFontSize() {
        val text = displayPrimary.text.toString()
        val length = text.length

        // Shrink font size only if text exceeds display size
        when {
            length > 10 -> displayPrimary.textSize = 24f  // Small font for long numbers
            else -> displayPrimary.textSize = 48f  // Default font size
        }
    }

    // Append numbers to the display, ensuring valid number input
    private fun appendNumber(number: String) {
        if (isNewOperation) {
            displayPrimary.text = number
            isNewOperation = false
        } else {
            // Prevent appending multiple decimal points
            if (number == "." && displayPrimary.text.contains(".")) {
                return
            }

            // Limit the length of the input to 15 characters
            if (displayPrimary.text.length >= 15) {
                return
            }

            displayPrimary.append(number)
        }
        adjustFontSize()  // Adjust font size if necessary
    }

    // Handle the input of operations (+, -, ×, ÷) and continuous operations
    private fun handleOperation(op: String) {
        if (firstValue != null) {
            calculateResult()  // Perform current calculation before applying a new operation
        }
        firstValue = displayPrimary.text.toString().toDoubleOrNull()  // Store the current input as firstValue
        operator = op  // Store the selected operator
        isNewOperation = true  // Mark as a new operation for the next input
        displaySecondary.text = "${displayPrimary.text} $operator"  // Display the ongoing operation in secondary display
    }

    // Calculate the result when "=" is pressed
    private fun calculateResult() {
        secondValue = displayPrimary.text.toString().toDoubleOrNull()  // Convert input to Double
        if (firstValue != null && secondValue != null && operator != null) {
            var result = when (operator) {
                "+" -> firstValue!! + secondValue!!
                "-" -> firstValue!! - secondValue!!
                "×" -> firstValue!! * secondValue!!
                "÷" -> firstValue!! / secondValue!!
                else -> 0.0
            }

            // Remove .0 if the result is an integer
            if (result % 1 == 0.0) {
                displayPrimary.text = result.toInt().toString()  // Convert to integer to remove .0
            } else {
                displayPrimary.text = result.toString()  // Keep the result as a decimal
            }

            // Reset for the next operation
            displaySecondary.text = ""
            firstValue = result  // Store the result for continuous operations
            isNewOperation = true
            operator = null
            adjustFontSize()  // Adjust the font size based on the result length
        }
    }

    // Clear all inputs and reset the calculator
    private fun clearCalculator() {
        displayPrimary.text = "0"
        displaySecondary.text = ""
        firstValue = null
        operator = null
        isNewOperation = true
        adjustFontSize()  // Reset to default font size
    }

    // Compute the square root of the current value
    private fun computeSquareRoot() {
        val value = displayPrimary.text.toString().toDoubleOrNull()  // Parse value to Double
        if (value != null) {
            val result = Math.sqrt(value)  // Calculate square root

            // Remove .0 if the result is an integer
            if (result % 1 == 0.0) {
                displayPrimary.text = result.toInt().toString()  // Convert to integer if applicable
            } else {
                displayPrimary.text = result.toString()
            }

            isNewOperation = true
            adjustFontSize()  // Adjust font size if necessary
        }
    }

    // Compute the square of the current value
    private fun computeSquare() {
        val value = displayPrimary.text.toString().toDoubleOrNull()  // Parse value to Double
        if (value != null) {
            val result = value * value  // Calculate the square

            // Remove .0 if the result is an integer
            if (result % 1 == 0.0) {
                displayPrimary.text = result.toInt().toString()  // Convert to integer if applicable
            } else {
                displayPrimary.text = result.toString()
            }

            isNewOperation = true
            adjustFontSize()  // Adjust font size if necessary
        }
    }

    // Remove the last digit of the current input
    private fun removeLastDigit() {
        val text = displayPrimary.text.toString()
        if (text.isNotEmpty()) {
            displayPrimary.text = text.dropLast(1)  // Remove the last character
            if (displayPrimary.text.isEmpty()) {
                displayPrimary.text = "0"  // Reset to 0 if input becomes empty
            }
            adjustFontSize()  // Adjust font size after removing a digit
        }
    }
}