package com.example.mybms

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //取得資料庫實體
        dbrw = MyDBHelper(this).writableDatabase
        //設定Adapter,並連結至ListView
        adapter = ArrayAdapter( this, android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.ListView).adapter = adapter
        //設定監聽器
        setListener()

    }

    private fun setListener() {
        val ed_book = findViewById<EditText>(R.id.ed_book)
        val ed_price = findViewById<EditText>(R.id.ed_Price)

        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            if (ed_book.length() < 1 || ed_price.length() < 1) {
                showToast(text = "欄位請勿空白")
            } else {
                try {
                    dbrw.execSQL(
                        "INSERT INTO myTable(book, price) VALUES(?,?)",
                        arrayOf(ed_book.text.toString(), ed_price.text.toString())
                    )
                    showToast(text = "新增: ${ed_book.text}, 價格: ${ed_price.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast(text = "新增失敗: $e")
                }
            }
        }

        // 設置更新按鈕的點擊事件監聽器
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            // 檢查書名和價格輸入框是否為空
            if (ed_book.length() < 1 || ed_price.length() < 1) {
                // 顯示提示訊息，告知使用者輸入欄位不能為空
                showToast(text = "欄位請勿空白")
            } else {
                try {
                    // 嘗試執行 SQL 更新語句，更新價格根據書名條件
                    dbrw.execSQL(
                        "UPDATE myTable SET price = ${ed_price.text} " +
                                "WHERE book LIKE '${ed_book.text}'"
                    )
                    // 顯示更新成功的訊息，包含更新的書名和價格
                    showToast(text = "更新:${ed_book.text}, 價格: ${ed_price.text}")
                    // 清空輸入框內容
                    cleanEditText()
                } catch (e: Exception) {
                    // 捕獲異常並顯示更新失敗的訊息
                    showToast(text = "更新失敗: $e")
                }
            }
        }

        // 設置刪除按鈕的點擊事件監聽器
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            // 檢查書名輸入框是否為空
            if (ed_book.length() < 1) {
                // 顯示提示訊息，告知使用者書名欄位不能為空
                showToast(text = "書名請勿空白")
            } else {
                try {
                    // 嘗試執行 SQL 刪除語句，根據書名條件刪除資料
                    dbrw.execSQL(
                        "DELETE FROM myTable " +
                                "WHERE book LIKE '${ed_book.text}'"
                    )
                    // 顯示刪除成功的訊息，包含刪除的書名
                    showToast(text = "刪除: ${ed_book.text}")
                    // 清空輸入框內容
                    cleanEditText()
                } catch (e: Exception) {
                    // 捕獲異常並顯示刪除失敗的訊息
                    showToast(text = "刪除失敗: $e")
                }
            }
        }

        findViewById<Button>(R.id.btn_query).setOnClickListener {
            // 根據書名輸入框是否有內容決定查詢的 SQL 語句
            val queryString = if (ed_book.length() < 1)
                "SELECT * FROM myTable" // 若無書名輸入，查詢所有資料
            else
                "SELECT * FROM myTable WHERE book LIKE '${ed_book.text}'" // 查詢符合書名的資料

            // 執行 SQL 查詢並取得 Cursor 物件
            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst() // 將 Cursor 指向查詢結果的第一筆資料
            items.clear() // 清空顯示用的資料集合

            // 顯示查詢到的資料數量
            showToast(text = "共有${c.count}筆資料")

            // 遍歷查詢結果，將資料加入 items 集合
            for (i in 0 until c.count) {
                items.add("書名: ${c.getString(0)}\t\t\t價格: ${c.getString(1)}") // 讀取書名和價格
                c.moveToNext() // 移動到下一筆資料
            }

            adapter.notifyDataSetChanged() // 通知 Adapter 資料已更新
            c.close() // 關閉 Cursor，釋放資源
        }


    }

    override fun onDestroy() {
        dbrw.close() // 關閉資料庫連線
        super.onDestroy()
    }

    private fun cleanEditText() {
        // 清空書名輸入框
        findViewById<EditText>(R.id.ed_book).setText("")
        // 清空價格輸入框
        findViewById<EditText>(R.id.ed_Price).setText("")
    }

    private fun showToast(text: String) {
        // 顯示長時間提示訊息
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}