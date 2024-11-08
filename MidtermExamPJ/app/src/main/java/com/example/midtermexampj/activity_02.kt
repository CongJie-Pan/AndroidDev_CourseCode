package com.example.midtermexampj

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_02 : AppCompatActivity() {

    private lateinit var textViewGreeting: TextView
    private lateinit var radioGroupMain: RadioGroup
    private lateinit var radioGroupSide: RadioGroup
    private lateinit var radioGroupDrink: RadioGroup
    private lateinit var buttonConfirm: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_02)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewGreeting = findViewById(R.id.textViewGreeting)
        radioGroupMain = findViewById(R.id.radioGroupMain)
        radioGroupSide = findViewById(R.id.radioGroupSide)
        radioGroupDrink = findViewById(R.id.radioGroupDrink)
        buttonConfirm = findViewById(R.id.buttonConfirm)

        val name = intent.getStringExtra("name") ?: ""
        textViewGreeting.text = "$name 您好"

        Log.d("Activity02", "Received name: $name")

        buttonConfirm.setOnClickListener {
            // Get selected items
            val mainDish = when (radioGroupMain.checkedRadioButtonId) {
                R.id.radioButtonChicken -> "咖哩唐揚雞飯"
                R.id.radioButtonDumplings -> "酸辣湯餃"
                R.id.radioButtonSteak -> "義大利煎烤雞飯"
                R.id.radioButtonKnifeCutNoodles -> "銷魂味僧刀削麵"
                else -> ""
            }

            val sideDish = when (radioGroupSide.checkedRadioButtonId) {
                R.id.radioButtonKimchi -> "藍帶豬排"
                R.id.radioButtonEgg -> "月亮蛋餅"
                R.id.radioButtonPork -> "香腸(大份)"
                R.id.radioButtonNoodles -> "越式春捲"
                else -> ""
            }

            val drink = when (radioGroupDrink.checkedRadioButtonId) {
                R.id.radioButtonMilktea -> "泰式鮮奶茶"
                R.id.radioButtonLemon -> "冰拿鐵"
                R.id.radioButtonBlackTea -> "錫蘭紅茶"
                R.id.radioButtonGreenTea -> "清心綠茶"
                else -> ""
            }

            val mainPrice = when (radioGroupMain.checkedRadioButtonId) {
                R.id.radioButtonChicken -> 120
                R.id.radioButtonKnifeCutNoodles -> 140
                R.id.radioButtonDumplings -> 80
                R.id.radioButtonSteak -> 130
                else -> 0
            }

            val sidePrice = when (radioGroupSide.checkedRadioButtonId) {
                R.id.radioButtonKimchi -> 60
                R.id.radioButtonEgg -> 55
                R.id.radioButtonPork -> 40
                R.id.radioButtonNoodles -> 70
                else -> 0
            }

            val drinkPrice = when (radioGroupDrink.checkedRadioButtonId) {
                R.id.radioButtonMilktea -> 45
                R.id.radioButtonLemon -> 50
                R.id.radioButtonBlackTea -> 40
                R.id.radioButtonGreenTea -> 35
                else -> 0
            }

            val total = mainPrice + sidePrice + drinkPrice

            if (mainDish.isEmpty() || sideDish.isEmpty() || drink.isEmpty()) {
                Toast.makeText(this, "請選擇主餐、小菜和飲料", Toast.LENGTH_SHORT).show()
                Log.d("Activity02", "Selection incomplete: Main: $mainDish, Side: $sideDish, Drink: $drink")
            } else {
                Log.d("Activity02", "Returning to MainActivity with total: $total")
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra("main", mainDish)
                    putExtra("side", sideDish)
                    putExtra("drink", drink)
                    putExtra("total", total)
                })
                finish()
            }
        }
    }
}