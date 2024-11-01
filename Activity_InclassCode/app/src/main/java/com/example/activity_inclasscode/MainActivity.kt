package com.example.activity_inclasscode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvFirst: TextView
    private lateinit var edFirst: EditText
    private lateinit var btnFirst: Button

    // 使用 registerForActivityResult 來啟動 Activity 並處理返回結果
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 取得 Activity_2 返回的資料
            val intent = result.data
            val returnedValue = intent?.getStringExtra("RETURNED_DATA")
            tvFirst.text = returnedValue // 更新 TextView 內容
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 初始化 UI 元件
        tvFirst = findViewById(R.id.textView1)
        edFirst = findViewById(R.id.editTextName) // 綁定到 TextInputEditText 的正確 ID
        btnFirst = findViewById(R.id.button)

        // 設置按鈕的點擊事件
        btnFirst.setOnClickListener {
            // 將用戶輸入的名稱傳遞到 Activity_2
            val intent = Intent(this, Activity_2::class.java).apply {
                putExtra("USER_NAME", edFirst.text.toString())
            }
            startForResult.launch(intent) // 啟動 Activity_2 並等待結果
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
