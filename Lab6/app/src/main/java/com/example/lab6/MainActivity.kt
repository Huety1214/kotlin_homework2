package com.example.lab6

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

/**
 * 優化重點：
 * 1. 未使用的 lambda 參數改成 _，編譯器不再警告，也看得出哪些參數有用到。
 * 2. 選項陣列用 List(5) { } 產生，不必手打五個字串。
 * 3. 共用的 AlertDialog.Builder 建立方式抽成 dialog(title)。
 * 4. 每個按鈕只用一次，直接 findViewById(...).setOnClickListener，不另外宣告變數。
 */
class MainActivity : AppCompatActivity() {

    private val items = Array(5) { "選項 ${it + 1}" }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun dialog(title: String) = AlertDialog.Builder(this).setTitle(title)

    private fun onClick(id: Int, action: (View) -> Unit) =
        findViewById<Button>(id).setOnClickListener { action(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        onClick(R.id.btnToast) { showToast("預設 Toast") }

        onClick(R.id.btnSnackBar) { view ->
            Snackbar.make(view, "按鈕式 Snackbar", Snackbar.LENGTH_SHORT)
                .setAction("按鈕") { showToast("已回應") }
                .show()
        }

        onClick(R.id.btnDialog1) {
            dialog("按鈕式 AlertDialog")
                .setMessage("AlertDialog 內容")
                .setNeutralButton("左按鈕") { _, _ -> showToast("左按鈕") }
                .setNegativeButton("中按鈕") { _, _ -> showToast("中按鈕") }
                .setPositiveButton("右按鈕") { _, _ -> showToast("右按鈕") }
                .show()
        }

        onClick(R.id.btnDialog2) {
            dialog("列表式 AlertDialog")
                .setItems(items) { _, i -> showToast("你選的是${items[i]}") }
                .show()
        }

        onClick(R.id.btnDialog3) {
            var position = 0
            dialog("單選式 AlertDialog")
                .setSingleChoiceItems(items, position) { _, i -> position = i }
                .setPositiveButton("確定") { _, _ -> showToast("你選的是${items[position]}") }
                .show()
        }
    }
}
