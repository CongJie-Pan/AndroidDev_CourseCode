package com.example.apibuilding

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btn_query: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn_query = findViewById(R.id.btn_query)
        btn_query.setOnClickListener {
            //避免按鈕再次被按
            btn_query.isEnabled = true
            //發送請求
            sendRequest()
        }
    }

    //發送請求
    private fun sendRequest() {

        //空氣品質指標
        val url = "https://api.italkutalk.com/api/air"
        //建立Request.Builder物件,利用url()方法將網址傳入,再建立Request物件
        val req = okhttp3.Request.Builder().url(url).build()
        //建立 OkHttpClient 物件,藉由 newCall()發送請求,並在 enqueue()接收回傳
        okhttp3.OkHttpClient().newCall(req).enqueue(object : okhttp3.Callback {
            //發送成功執行此方法
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                //使用 response.body?.string()取得 JSON 字串
                val json = response.body?.string()
                //建立 Gson 並使用其 fromJson()方法,將 JSON 字串以 MyObject 格式輸出
                val myObject = com.google.gson.Gson().fromJson(json, MyObject::class.java)
                //顯示結果
                showDialog(myObject)
            }

            //發送失敗執行此方法
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                runOnUiThread {
                    //開啟按鈕可再次查詢
                    btn_query.isEnabled = true
                    //顯示錯誤訊息
                    android.widget.Toast.makeText(
                        this@MainActivity,
                        "查詢失敗$e",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }


        })
    }

    //顯示結果
    private fun showDialog(myObject: MyObject) {
        //建立一個字串陣列,用於存放 SiteName 與 Status 資訊
        val items = mutableListOf<String>()
        //將 API 資料取出並建立字串,並存放到字串陣列
        myObject.result.records.forEach { data ->
            items.add("地區:${data.SiteName},狀態:${data.Status}")
        }

        //切換到主執行緒將畫面更新
        runOnUiThread {
            //開啟按鈕可再次查詢
            btn_query.isEnabled = true
            //建立 AlertDialog 物件並顯示字串陣列
            AlertDialog.Builder(this@MainActivity)
                .setTitle("空氣品質指標")
                .setItems(items.toTypedArray(), null)
                .show()
        }
    }

}