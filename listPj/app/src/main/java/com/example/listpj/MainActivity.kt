package com.example.listpj

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        val items = listOf(
            item(R.drawable.apple, "蘋果", 30),
            item(R.drawable.banana, "香蕉", 20),
            item(R.drawable.orange, "橘子", 25),
            // 添加更多項目...
        )

        val adapter = MyAdapter(this, R.layout.adapter_vertical, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            Toast.makeText(this, "${selectedItem.name}: ${selectedItem.price}元", Toast.LENGTH_SHORT).show()
        }
    }
}

/* 原始內容 */
/*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val gridView = findViewById<GridView>(R.id.gridView)
        val listView = findViewById<ListView>(R.id.listView)
        val count = ArrayList<String>()
        val items = ArrayList<item>()
        val priceRange = 10..100
        val array = resources.obtainTypedArray(R.array.image_list)
        for (index in 0 until array.length()) {
            val photo = array.getResourceId(index, 0)
            val name = "${index + 1}"
            val price = priceRange.random()
            count.add("$name")
            items.add(item(photo, name, price))
        }

        array.recycle()

        items.shuffle()

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, count)
        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, R.layout.adapter_vertical, items)
        listView.adapter = MyAdapter(this, R.layout.adapter_horizontal, items)
    }
}
*/
