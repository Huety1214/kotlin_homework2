package com.example.lab5

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private fun log(event: String) = Log.e("MainActivity", event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        log("onCreate")

        // apply：一次設定 ViewPager2 的多個屬性
        findViewById<ViewPager2>(R.id.viewPager2).apply {
            adapter = ViewPagerAdapter(this@MainActivity)
            offscreenPageLimit = 1 // 預先載入鄰近頁面
        }
    }

    override fun onRestart() { super.onRestart(); log("onRestart") }
    override fun onStart() { super.onStart(); log("onStart") }
    override fun onResume() { super.onResume(); log("onResume") }
    override fun onPause() { super.onPause(); log("onPause") }
    override fun onStop() { super.onStop(); log("onStop") }
    override fun onDestroy() { super.onDestroy(); log("onDestroy") }
}
