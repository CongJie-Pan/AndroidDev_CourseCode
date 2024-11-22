package com.example.inclasstoastpj

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import com.example.inclasstoastpj.databinding.ActivityMainBinding
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    // Declare view binding variable
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all buttons
        setupDefaultToast()
        setupCustomToast()
        setupSnackbar()
        setupAlertDialog()
        setupListAlertDialog()
        setupSingleChoiceAlertDialog()
    }

    // Setup default Toast button
    private fun setupDefaultToast() {
        findViewById<Button>(R.id.btn_Toast).setOnClickListener {
            Toast.makeText(this, "這是預設Toast", Toast.LENGTH_SHORT).show()
        }
    }

    // Setup custom Toast button
    private fun setupCustomToast() {
        findViewById<Button>(R.id.btn_Custom).setOnClickListener {
            // Inflate custom layout
            val inflater = layoutInflater
            val layout = inflater.inflate(R.layout.custom_toast, null)

            // Create and setup custom Toast
            Toast(this@MainActivity).apply {
                setGravity(Gravity.CENTER, 0, 0)
                duration = Toast.LENGTH_SHORT
                view = layout
                show()
            }
        }
    }

    // Setup Snackbar button
    private fun setupSnackbar() {
        findViewById<Button>(R.id.btn_Snackbar).setOnClickListener {
            Snackbar.make(it, "這是按鈕式Snackbar", Snackbar.LENGTH_LONG)
                .setAction("按鈕") {
                    Toast.makeText(this, "已點擊Snackbar按鈕", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    // Setup button style AlertDialog
    private fun setupAlertDialog() {
        findViewById<Button>(R.id.btn_Dialog).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("按鈕式對話框")
                .setMessage("這是一個按鈕式AlertDialog")
                .setPositiveButton("確定") { dialog, _ ->
                    Toast.makeText(this, "已點擊確定", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Setup list style AlertDialog
    private fun setupListAlertDialog() {
        findViewById<Button>(R.id.btn_Dialog2).setOnClickListener {
            val items = arrayOf("選項1", "選項2", "選項3")
            AlertDialog.Builder(this)
                .setTitle("清單式對話框")
                .setItems(items) { dialog, which ->
                    val userInput = findViewById<EditText>(R.id.et_user_input).text.toString()
                    Toast.makeText(this, "$userInput 你好，你選的是${items[which]}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }
    }

    // Setup single choice AlertDialog
    private fun setupSingleChoiceAlertDialog() {
        findViewById<Button>(R.id.btn_Dialog3).setOnClickListener {
            val items = arrayOf("選項1", "選項2", "選項3")
            var selectedItem = 0

            AlertDialog.Builder(this)
                .setTitle("單選式對話框")
                .setSingleChoiceItems(items, selectedItem) { _, which ->
                    selectedItem = which
                }
                .setPositiveButton("確定") { dialog, _ ->
                    Toast.makeText(this, "已選擇${items[selectedItem]}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}