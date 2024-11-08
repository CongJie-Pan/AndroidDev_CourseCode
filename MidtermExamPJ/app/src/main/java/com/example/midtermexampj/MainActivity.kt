package com.example.midtermexampj

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var buttonStart: Button
    private lateinit var textViewMainDish: TextView
    private lateinit var textViewSideDish: TextView
    private lateinit var textViewDrink: TextView
    private lateinit var textViewTotal: TextView

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                textViewMainDish.text = "主餐：${it.getStringExtra("main")}"
                textViewSideDish.text = "小菜：${it.getStringExtra("side")}"
                textViewDrink.text = "飲料：${it.getStringExtra("drink")}"
                textViewTotal.text = "總價：NTD ${it.getIntExtra("total", 0)}"
            }
        } else {
            Toast.makeText(this, "未選擇任何餐點", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editTextName = findViewById(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        buttonStart = findViewById(R.id.buttonStart)
        textViewMainDish = findViewById(R.id.textViewMainDish)
        textViewSideDish = findViewById(R.id.textViewSideDish)
        textViewDrink = findViewById(R.id.textViewDrink)
        textViewTotal = findViewById(R.id.textViewTotal)

        // Set click listener for start button
        buttonStart.setOnClickListener {
            if (editTextName.text.isEmpty() || editTextPhone.text.isEmpty()) {
                Toast.makeText(this, "請填寫姓名和電話", Toast.LENGTH_SHORT).show()
            } else {
                val name = editTextName.text.toString()
                val phone = editTextPhone.text.toString()

                Log.d("MainActivity", "Starting Activity02 with name: $name and phone: $phone")

                val intent = Intent(this, activity_02::class.java).apply {
                    putExtra("name", name)
                    putExtra("phone", phone)
                }
                startForResult.launch(intent)
            }
        }
    }
}
