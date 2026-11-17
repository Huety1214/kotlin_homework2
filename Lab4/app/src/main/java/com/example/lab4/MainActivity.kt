package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 優化重點：
 * 1. Intent 的 key 統一放在 SecActivity 的 companion object，避免字串打錯。
 * 2. tvMeal 只 findViewById 一次（lazy），不在每次回傳時重找。
 * 3. 使用 ?.let 處理 null，回傳資料為空時不會顯示 "null"。
 */
class MainActivity : AppCompatActivity() {

    private val tvMeal by lazy { findViewById<TextView>(R.id.tvMeal) }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        result.data?.let { data ->
            val drink = data.getStringExtra(SecActivity.KEY_DRINK) ?: "無"
            val sugar = data.getStringExtra(SecActivity.KEY_SUGAR) ?: "無"
            val ice = data.getStringExtra(SecActivity.KEY_ICE) ?: "無"
            tvMeal.text = "飲料：$drink\n\n甜度：$sugar\n\n冰塊：$ice"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnChoice).setOnClickListener {
            startForResult.launch(Intent(this, SecActivity::class.java))
        }
    }
}
