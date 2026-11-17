package com.example.lab5

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 優化重點：頁面清單改成 List<() -> Fragment>，新增頁面只要加一行，
 * getItemCount 自動跟著改變，不必同時改兩個地方。
 */
class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val pages: List<() -> Fragment> = listOf(
        ::FirstFragment,
        ::SecondFragment,
        ::ThirdFragment
    )

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]()
}
