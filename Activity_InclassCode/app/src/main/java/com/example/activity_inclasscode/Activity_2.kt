package com.example.activity_inclasscode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_2 : AppCompatActivity() {

    private lateinit var tvSec: TextView
    private lateinit var edSec: EditText
    private lateinit var btnSec: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)

        // 初始化 UI 元件
        tvSec = findViewById(R.id.textView2)
        edSec = findViewById(R.id.editTextStudentId) // 綁定到 TextInputEditText 的正確 ID
        btnSec = findViewById(R.id.button)

        // 接收 MainActivity 傳遞的名稱並設定顯示文字
        val userName = intent.getStringExtra("USER_NAME")
        tvSec.text = "你好 $userName，請輸入學號"

        // 設置返回按鈕的點擊事件
        btnSec.setOnClickListener {
            // 準備返回的 Intent，包含用戶輸入的學號
            val resultIntent = Intent().apply {
                putExtra("RETURNED_DATA", edSec.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent) // 設置結果
            finish() // 結束 Activity_2
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
