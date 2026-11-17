package com.example.lab5

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * 優化重點：原本三個 Fragment 各自覆寫 11 個生命週期方法，程式碼幾乎一模一樣。
 * 抽出共同父類別，子類別只需提供 Layout，Log 的 TAG 自動使用類別名稱。
 */
abstract class LifecycleFragment(@LayoutRes private val layoutRes: Int) : Fragment() {

    private val logTag: String = javaClass.simpleName
    private fun log(event: String) = Log.e(logTag, event)

    override fun onAttach(context: Context) { super.onAttach(context); log("onAttach") }
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); log("onCreate") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        log("onCreateView")
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState); log("onViewCreated")
    }
    override fun onStart() { super.onStart(); log("onStart") }
    override fun onResume() { super.onResume(); log("onResume") }
    override fun onPause() { super.onPause(); log("onPause") }
    override fun onStop() { super.onStop(); log("onStop") }
    override fun onDestroyView() { super.onDestroyView(); log("onDestroyView") }
    override fun onDestroy() { super.onDestroy(); log("onDestroy") }
    override fun onDetach() { super.onDetach(); log("onDetach") }
}
