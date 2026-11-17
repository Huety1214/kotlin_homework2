package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {

    companion object {
        const val KEY_DRINK = "drink"
        const val KEY_SUGAR = "sugar"
        const val KEY_ICE = "ice"
    }

    // 擴充函式：取得 RadioGroup 目前選取的文字，取代重複的 findViewById<RadioButton>(...)
    private fun RadioGroup.checkedText(): String =
        findViewById<RadioButton>(checkedRadioButtonId).text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val edDrink = findViewById<EditText>(R.id.edDrink)
        val rgSugar = findViewById<RadioGroup>(R.id.rgSugar)
        val rgIce = findViewById<RadioGroup>(R.id.rgIce)

        findViewById<Button>(R.id.btnSend).setOnClickListener {
            val drink = edDrink.text.toString().trim()
            if (drink.isEmpty()) {
                Toast.makeText(this, "請輸入飲料名稱", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // apply 讓 Intent 的設定集中在同一個區塊
            val result = Intent().apply {
                putExtra(KEY_DRINK, drink)
                putExtra(KEY_SUGAR, rgSugar.checkedText())
                putExtra(KEY_ICE, rgIce.checkedText())
            }
            setResult(RESULT_OK, result)
            finish()
        }
    }
}
